package code.cf1v1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "duels")
public class Duel {
    @Id
    private ObjectId id;
    private String opponent1;
    private String opponent2;
    @DBRef
    private Question question;
    long duelDurationSeconds;
    private Integer rating;
    private String result;
    private String roomId;
    private String joiningUsername;
}
