package com.neo.gymification.controllers;

import com.neo.gymification.models.GUser;
import com.neo.gymification.models.Login;
import com.neo.gymification.services.LoginServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/login")
public class LoginController {

  private LoginServiceImpl loginService;

  @Autowired
  public LoginController(LoginServiceImpl loginService) {
    this.loginService = loginService;
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public GUser login(@RequestBody Login login) {
    return loginService.login(login);
  }
}
