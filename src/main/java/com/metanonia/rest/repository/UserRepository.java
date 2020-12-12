package com.metanonia.rest.repository;

import com.metanonia.rest.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByUid(String uid);
}
