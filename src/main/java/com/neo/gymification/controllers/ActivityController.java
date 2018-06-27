package com.neo.gymification.controllers;

import com.neo.gymification.models.Activity;
import com.neo.gymification.services.ActivityServiceImpl;

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

  @RequestMapping(
      value = "users/{hwAddress}/activities",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Activity createUserActivity(@RequestBody Activity activity,
                                     @PathVariable("hwAddress") String hwAddress) {

    return activityService.createUserActivity(activity, hwAddress);
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
