package com.neo.gymification.services;

import com.neo.gymification.models.Activity;
import com.neo.gymification.models.ActivityType;
import com.neo.gymification.models.GUser;
import com.neo.gymification.models.TaskType;
import com.neo.gymification.models.WeeklyTask;
import com.neo.gymification.repositories.ActivityRepository;
import com.neo.gymification.repositories.UserRepository;
import com.neo.gymification.repositories.WeeklyTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityServiceImpl {

  private UserRepository userRepository;

  private ActivityRepository activityRepository;
  private WeeklyTaskRepository taskRepository;

  @Autowired
  public ActivityServiceImpl(UserRepository userRepository, ActivityRepository
      activityRepository) {
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
    activity.setActivityType(ActivityType.GYM_VISIT);
    user.setPoints(user.getPoints() + activityPoints);
    activity = activityRepository.save(activity);
    return activity;
  }

  private Long convertTimeToPoints(Long time) {
    return time/1000;
  }

  public List<Activity> getUserActivitiesByUserName(String hwAddress) {
    userRepository.findByHwAddress(hwAddress)
        .orElseThrow(() -> new RuntimeException("hwAddress is not present in DB"));
    return activityRepository.findByUserHwAddress(hwAddress);
  }

  public List<Activity> getUserActivitiesByUserNameAfterDate(String hwAddress, long date) {
    userRepository.findByHwAddress(hwAddress)
        .orElseThrow(() -> new RuntimeException("hwAddress is not present in DB"));
    return activityRepository.findByUserHwAddressAndDateGreaterThanOrderByDate(hwAddress, date);
  }

  public void assingPointsFromTask(WeeklyTask t, String hwAddress) {
    if (activityRepository.findByTaskIdAndUser_HwAddress(t.getUuid(), hwAddress).isPresent())
      return;
    Activity activity = new Activity();
    activity.setId(UUID.randomUUID());
    activity.setTaskId(t.getUuid());
    activity.setActivityType(ActivityType.WEEKLY_TASK);
    activity.setTime(t.getCurrentProgress());
    activity.setPoints(t.getPoints());
    activity.setDate(new Date().getTime());
    GUser user = userRepository.findByHwAddress(hwAddress).get();
    user.setPoints(user.getPoints() + t.getPoints());
    activity.setUser(user);

    activityRepository.save(activity);
  }
}