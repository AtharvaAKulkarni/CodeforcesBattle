package code.cf1v1.service;

import code.cf1v1.entity.Duel;
import code.cf1v1.entity.Question;
import code.cf1v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DuelService {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserRepository userRepository;
    public Duel createDuel(String username1, String username2, long duelDurationSeconds, Integer rating){
        ResponseEntity<Question> duelQuestion=questionService.findQuestionWithRating(rating, username1, username2);
        Question q;
        if(!duelQuestion.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Question not found");
        }
        Duel duel=new Duel();
        duel.setQuestion(duelQuestion.getBody());
        duel.setOpponent1(username1);
        duel.setOpponent2(username2);
        duel.setDuelDurationSeconds(duelDurationSeconds);
        duel.setRating(rating);
        return duel;
    }
}

