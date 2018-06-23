package com.neo.gymification.services;

import com.neo.gymification.models.Login;
import com.neo.gymification.models.User;
import com.neo.gymification.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {

  private UserRepository userRepository;

  @Autowired
  public LoginServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User login(Login login) {
    User dbUser = userRepository.findById(login.getUserName()).get();

    if (dbUser == null) {
      throw new RuntimeException("User is not present in DB");
    }
    if (dbUser.getPassword().equals(login.getPassword())) {
      return dbUser;
    }
    throw new RuntimeException("Password is not correct");
  }
}
