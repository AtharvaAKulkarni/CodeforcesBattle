package code.cf1v1.controller;
import code.cf1v1.entity.Question;
import code.cf1v1.entity.User;
import code.cf1v1.repository.UserRepository;
import code.cf1v1.service.QuestionService;
import code.cf1v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/{rating}/{username1}/{username2}")
    public ResponseEntity<Question> getQuestion(@PathVariable Integer rating, @PathVariable String username1, @PathVariable String username2){
        return questionService.findQuestionWithRating(rating, username1, username2);
    }
}
