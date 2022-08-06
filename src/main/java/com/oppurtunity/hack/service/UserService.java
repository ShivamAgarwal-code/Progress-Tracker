package com.oppurtunity.hack.service;

import com.oppurtunity.hack.entities.User;
import com.oppurtunity.hack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User save (User user) {
		return userRepository.save(user);
	}
	public User getUser(User user) {
		return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
	}
}
