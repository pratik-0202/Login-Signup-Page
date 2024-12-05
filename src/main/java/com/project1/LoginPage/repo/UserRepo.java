package com.project1.LoginPage.repo;

import com.project1.LoginPage.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<Users, ObjectId> {

    Users findByUserName(String userName);
}
