package code.cf1v1.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "user")
@Data
public class User {
    @Id
    private String username;
    private String password;
    List<String> friends=new ArrayList<>();
}
