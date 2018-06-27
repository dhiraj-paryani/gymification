package com.neo.gymification.controllers;

import com.neo.gymification.models.Activity;
import com.neo.gymification.models.GUser;
import com.neo.gymification.services.ActivityServiceImpl;
import com.neo.gymification.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ActivityController {

  @Autowired
  private ActivityServiceImpl activityService;
  @Autowired
  UserServiceImpl userService;

  @RequestMapping(
      value = "users/{hwAddress}/activities",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Activity createUserActivity(@RequestBody Activity activity,
                                     @PathVariable("hwAddress") String hwAddress) {

    GUser user = userService.getUserByHwAddress(hwAddress);
    user.setActive(false);
    userService.updateUser(user);
    return activityService.createUserActivity(activity, hwAddress);
  }

  @RequestMapping(
      value = "users/{hwAddress}/activities/start",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public GUser setUserActive(@PathVariable("hwAddress") String hwAddress) {
    GUser user = userService.getUserByHwAddress(hwAddress);
    user.setActive(true);
    return userService.updateUser(user);
  }

  @RequestMapping(
      value = "users/{hwAddress}/activities",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public List<Activity> getUserActivitiesByUserName(@PathVariable("hwAddress") String hwAddress) {
    return activityService.getUserActivitiesByUserName(hwAddress);
  }

}
