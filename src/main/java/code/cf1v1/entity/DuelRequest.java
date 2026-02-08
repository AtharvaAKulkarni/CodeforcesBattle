package code.cf1v1.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DuelRequest {
    String user1;
    String user2;
}
