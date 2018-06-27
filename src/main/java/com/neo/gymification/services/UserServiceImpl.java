package com.neo.gymification.services;

import com.neo.gymification.models.GUser;
import com.neo.gymification.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

  public GUser createUser(GUser user) {
    if( userRepository.findById(user.getUserName()).isPresent() ) {
      throw new RuntimeException("User is already present in the DB.");
    }

    return userRepository.save(user);
  }

  public GUser getUserByUsername(String username) {
    return userRepository.findByUserName(username).get();
  }


  public List<GUser> getAllUsers(Pageable pageable) {
    List<GUser> allUsers = new ArrayList<>();
    userRepository.findAll(pageable).forEach(allUsers::add);
    return allUsers;
  }
}
