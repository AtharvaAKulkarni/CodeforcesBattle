package code.cf1v1.service;

import code.cf1v1.entity.PastQuestionResponse;
import code.cf1v1.entity.Question;
import code.cf1v1.entity.QuestionIdentifier;
import code.cf1v1.entity.User;
import code.cf1v1.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component

public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Question> findQuestionWithRating(Integer rating, String username1, String username2){
        try{
            List<QuestionIdentifier> qUser1 = checkPastSubmissions(username1);
            List<QuestionIdentifier> qUser2 = checkPastSubmissions(username2);
            for (int i = 0; i < 10; i++) {
                Question question = questionRepository.findRandomByRating(rating);
                if (question == null) {
                    throw new Exception("No question found for rating " + rating);
                }
                QuestionIdentifier currQuestion = new QuestionIdentifier();
                currQuestion.setContestId(question.getContestId());
                currQuestion.setIndex(question.getIndex());
                boolean one = qUser1.stream().anyMatch(q -> q.equals(currQuestion));
                boolean two = qUser2.stream().anyMatch(q -> q.equals(currQuestion));
                if (!(one || two)) return new ResponseEntity<>(question, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private List<QuestionIdentifier> checkPastSubmissions(String username) throws Exception {
        String url = "https://codeforces.com/api/user.status?handle=<handle>&from=1&count=30";
        url = url.replace("<handle>", username);
        ResponseEntity<PastQuestionResponse> pastQuestionsByUser = restTemplate.exchange(url, HttpMethod.GET, null, PastQuestionResponse.class);
        if (pastQuestionsByUser.hasBody()){
            PastQuestionResponse response=pastQuestionsByUser.getBody();
            List<PastQuestionResponse.ResultObject> resultObjects = response.getResult();
            List<QuestionIdentifier> solvedQuestions = resultObjects.stream()
                    .map(PastQuestionResponse.ResultObject::getProblem)
                    .distinct()
                    .toList();
            return  solvedQuestions;
        }

        throw new RuntimeException("No unsolved question found for given rating");
    }
}
