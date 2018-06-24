package com.neo.gymification.services;

import com.neo.gymification.models.Activity;
import com.neo.gymification.models.GUser;
import com.neo.gymification.repositories.ActivityRepository;
import com.neo.gymification.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityServiceImpl {

  private UserRepository userRepository;

  private ActivityRepository activityRepository;

  @Autowired
  public ActivityServiceImpl(UserRepository userRepository,
                             ActivityRepository activityRepository) {
    this.userRepository = userRepository;
    this.activityRepository = activityRepository;
  }

  public Activity createUserActivity(Activity activity, String hwAddress) {

    GUser user = userRepository.findByHwAddress(hwAddress)
        .orElseThrow(() -> new RuntimeException("hwAddress is not present in the DB"));

    activity.setId(UUID.randomUUID());
    activity.setUser(user);
    Long activityPoints = convertTimeToPoints(activity.getTime());
    activity.setPoints(activityPoints);
    user.setPoints(user.getPoints() + activityPoints);
    activity = activityRepository.save(activity);

    return activity;
  }

  private Long convertTimeToPoints(Long time) {
    return time/1000;
  }

  public List<Activity> getUserActivitiesByUserName(String hwAddress) {
    GUser user = userRepository.findByHwAddress(hwAddress)
        .orElseThrow(() -> new RuntimeException("hwAddress is not present in DB"));
    return activityRepository.findByUserHwAddress(hwAddress);
  }
}