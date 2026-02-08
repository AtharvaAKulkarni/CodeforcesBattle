package code.cf1v1.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PastQuestionResponse {
    List<ResultObject> result;
    @Getter
    @Setter
    public static class ResultObject{
        QuestionIdentifier problem;
    }
}
