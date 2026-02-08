package code.cf1v1.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DuelRoomService {
    private final Map<String, Set<String>> rooms=new ConcurrentHashMap<>();

    public String createRoom(String user1, String user2){
        String roomId= UUID.randomUUID().toString();
        rooms.put(roomId, ConcurrentHashMap.newKeySet());
        return roomId;
    }

    public void joinRoom(String roomId, String username){
        rooms.get(roomId).add(username);
    }

    public boolean isReady(String roomId){
        return rooms.get(roomId).size()==2;
    }


}
