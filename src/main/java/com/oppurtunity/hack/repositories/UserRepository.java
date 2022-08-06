package com.oppurtunity.hack.repositories;

import org.springframework.stereotype.Repository;

import com.oppurtunity.hack.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface UserRepository extends MongoRepository<User, Integer>{

	User findByUserNameAndPassword(String userName, String password);
}
