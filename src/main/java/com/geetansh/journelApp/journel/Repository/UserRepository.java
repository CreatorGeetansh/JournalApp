package com.geetansh.journelApp.journel.Repository;

import com.geetansh.journelApp.journel.entity.JournalEntry;
import com.geetansh.journelApp.journel.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
   User findByUserName(String username);

   void deleteByUserName(String username);
}
