package code.cf1v1.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collection = "questions")
public class Question {
    @Id
    private ObjectId id;
    private Integer contestId;
    private String index;
    private String name;
    private String type;
    private Integer rating;
    private List<String> tags;
}
