package com.neo.gymification.controllers;

import com.neo.gymification.models.BadgeName;
import com.neo.gymification.models.UserBadge;
import com.neo.gymification.services.UserBadgeServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class UserBadgeController {

  @Autowired
  private UserBadgeServiceImpl userBadgeService;

  @RequestMapping(
      value = "/users/{userName}/badges",
      method = RequestMethod.POST
  )
  @ResponseBody
  public void assignBadgeToUser(@PathVariable("userName") String userName,
                                @RequestBody UserBadge userBadge) {
    userBadgeService.assignBadgeToUser(userName, userBadge);
  }

  @RequestMapping(
      value = "/users/{userName}/badges",
      method = RequestMethod.GET
  )
  @ResponseBody
  public List<UserBadge> getBadgesByUserName(@PathVariable("userName") String userName) {
    return userBadgeService.getBadgesByUserName(userName);
  }


  @RequestMapping(
      value = "/badges",
      method = RequestMethod.GET
  )
  @ResponseBody
  public List<UserBadge> getAllBadges() {
    return userBadgeService.getAllBadges();
  }

  @RequestMapping(
      value = "/badges/{badgeName}",
      method = RequestMethod.GET,
      produces = MediaType.IMAGE_PNG_VALUE
  )
  @ResponseBody
  public byte[] getBadgeImage(@PathVariable("badgeName") BadgeName badgeName) throws IOException {
    return userBadgeService.getBadgeImage(badgeName);
  }

}
