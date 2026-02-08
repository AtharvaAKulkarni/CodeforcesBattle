package code.cf1v1.repository;

import code.cf1v1.entity.Duel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DuelRepository extends MongoRepository<Duel, ObjectId> {

}
