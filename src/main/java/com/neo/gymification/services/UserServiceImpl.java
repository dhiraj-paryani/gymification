package com.neo.gymification.services;

import com.neo.gymification.models.User;
import com.neo.gymification.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

  private UserRepository userRepository;

  @Autowired
  public  UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User getUserByUserName(String userName) {
    return userRepository.findById(userName).get();
  }

  public List<User> getAllUsers() {
    List<User> allUsers = new ArrayList<>();
    userRepository.findAll().forEach(allUsers::add);
    return allUsers;
  }
}
