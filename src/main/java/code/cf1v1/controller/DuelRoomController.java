package code.cf1v1.controller;

import code.cf1v1.entity.Duel;
import code.cf1v1.entity.DuelRequest;
import code.cf1v1.entity.Question;
import code.cf1v1.repository.DuelRepository;
import code.cf1v1.service.DuelRoomService;
import code.cf1v1.service.DuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class DuelRoomController {

    @Autowired
    private DuelRoomService duelRoomService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    DuelService duelService;
    @Autowired
    DuelRepository duelRepository;

    private Duel createDuel(Duel duel){
        Duel d=duelService.createDuel(duel.getOpponent1(), duel.getOpponent2(), duel.getDuelDurationSeconds(), duel.getRating());
        d.setRoomId(duel.getRoomId());
        if(d!=null) duelRepository.save(d);
        return d;
    }

    @MessageMapping("/duel/create-room")
    public void createRoom(DuelRequest req){
        String roomId= duelRoomService.createRoom(req.getUser1(), req.getUser2());
        messagingTemplate.convertAndSend("/queue/duel-room/"+req.getUser2(), Map.of(
                "roomId", roomId,
                "challenger", req.getUser1()
        ));
        messagingTemplate.convertAndSend("/queue/duel-room/"+req.getUser1(), Map.of(
                "roomId", roomId,
                "challenger", req.getUser1()
        ));
    }

    @MessageMapping("/duel/join")
    public void joinRoom(@Payload Duel duel){
        String username=duel.getJoiningUsername();
        duelRoomService.joinRoom(duel.getRoomId(), username);

        if(duelRoomService.isReady(duel.getRoomId())){
            Duel d=createDuel(duel);
            messagingTemplate.convertAndSend("/topic/duel/"+duel.getRoomId(), d);
        }
    }
}
