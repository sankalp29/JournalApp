package com.thejournalapp.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.thejournalapp.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    User findByUsername(String username);
}
