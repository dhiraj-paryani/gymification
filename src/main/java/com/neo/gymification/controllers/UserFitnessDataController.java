package com.neo.gymification.controllers;

import com.neo.gymification.models.UserFitnessData;
import com.neo.gymification.services.UserFitnessDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user-fitness-data")
public class UserFitnessDataController {

  private UserFitnessDataService userFitnessDataService;

  @Autowired
  public UserFitnessDataController(UserFitnessDataService userFitnessDataService) {
    this.userFitnessDataService = userFitnessDataService;
  }

  @RequestMapping(
      value = "/{hwAddress}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public UserFitnessData getUserFitnessData(@PathVariable("hwAddress") String userName,
                                            @RequestParam("date") Long date) {
    return userFitnessDataService.getUserFitnessData(userName,date);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public UserFitnessData updateUserFitnessData(@RequestBody UserFitnessData userFitnessData) {
    return userFitnessDataService.updateUserFitnessData(userFitnessData);
  }
}
