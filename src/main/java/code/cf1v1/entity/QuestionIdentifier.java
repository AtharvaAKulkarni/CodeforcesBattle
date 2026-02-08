package code.cf1v1.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class QuestionIdentifier {
    private Integer contestId;
    private String index;
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        QuestionIdentifier that=(QuestionIdentifier) o;
        return Objects.equals(contestId, that.contestId) && Objects.equals(index, that.index);
    }
    @Override
    public int hashCode() {
        return Objects.hash(contestId, index);
    }
}
