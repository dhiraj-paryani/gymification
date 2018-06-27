package com.neo.gymification.controllers;

import com.neo.gymification.models.GUser;
import com.neo.gymification.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

  private UserServiceImpl userService;

  @Autowired
  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  /*
   * Create User API.
   */
  @RequestMapping(
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public GUser createUser(@RequestBody GUser user) {
    return userService.createUser(user);
  }

  /*
   * Get All Users API.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public List<GUser> getAllUsers(Pageable pageable) {
    return userService.getAllUsers(pageable);
  }

  @RequestMapping(
      value = "/active",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public List<GUser> getAllActiveUsers() {
    return userService.getAllActiveUsers();
  }



  @RequestMapping(
      value = "/search/{username}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public GUser getUserByUsername(@PathVariable("username") String username) {
    return userService.getUserByUsername(username);
  }

}
