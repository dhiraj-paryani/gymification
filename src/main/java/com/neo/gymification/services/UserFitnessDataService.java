package com.neo.gymification.services;

import com.neo.gymification.models.Activity;
import com.neo.gymification.models.ActivityType;
import com.neo.gymification.models.GUser;
import com.neo.gymification.models.UserFitnessData;
import com.neo.gymification.repositories.ActivityRepository;
import com.neo.gymification.repositories.UserFitnessDataRepository;
import com.neo.gymification.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserFitnessDataService {

  @Autowired
  private UserFitnessDataRepository userFitnessDataRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ActivityRepository activityRepository;

  public UserFitnessData getUserFitnessData(String hwAddress, Long date) {
    Date dateObject = new Date(date);
    Long dateToSaveInDb = Long.parseLong(
        dateObject.getDate() + "" + dateObject.getMonth() + dateObject.getYear());
    System.out.print(dateToSaveInDb);

    if (userFitnessDataRepository.findByDateAndHwAddress(dateToSaveInDb, hwAddress).isPresent()) {
      return userFitnessDataRepository.findByDateAndHwAddress(dateToSaveInDb, hwAddress).get();
    } else {
      UserFitnessData userFitnessData = new UserFitnessData();
      userFitnessData.setId(UUID.randomUUID());
      userFitnessData.setDate(dateToSaveInDb);
      userFitnessData.setHwAddress(hwAddress);
      userFitnessData.setSteps(Long.parseLong("0"));
      userFitnessData.setCalories(Long.parseLong("0"));
      userFitnessDataRepository.save(userFitnessData);
      return userFitnessData;
    }
  }

  public UserFitnessData updateUserFitnessData(UserFitnessData userFitnessData) {

    Date dateObject = new Date(userFitnessData.getDate());

    Long dbDate = Long.parseLong(
        dateObject.getDate() + "" + dateObject.getMonth() + dateObject.getYear());

    if (userFitnessDataRepository.
        findByDateAndHwAddress(dbDate, userFitnessData.getHwAddress()).isPresent()) {

      return updateCurrentUserFitnessData(
          userFitnessDataRepository.findByDateAndHwAddress(dbDate, userFitnessData.getHwAddress()).get(),
          userFitnessData);

    } else {
      userFitnessData.setDate(dbDate);
      userFitnessData.setId(UUID.randomUUID());
      /*addPointsToUserData(userFitnessData.getHwAddress(),
          userFitnessData.getSteps()/10 + userFitnessData.getCalories()/5);*/

      createActivityForFitnessData(userFitnessData.getHwAddress(),
          userFitnessData.getId(),
          ActivityType.STEPS,
          userFitnessData.getSteps(),
          userFitnessData.getSteps()/10);

      createActivityForFitnessData(userFitnessData.getHwAddress(),
          userFitnessData.getId(),
          ActivityType.CALORIES,
          userFitnessData.getCalories(),
          userFitnessData.getCalories()/5);

      return userFitnessDataRepository.save(userFitnessData);
    }
  }

  private UserFitnessData updateCurrentUserFitnessData(UserFitnessData dbUserFitnessData,
                                            UserFitnessData requestedUserFitnessData) {

    Long dbUserSteps = dbUserFitnessData.getSteps();
    Long requestUserSteps = requestedUserFitnessData.getSteps();

    Long dbUserCalories = dbUserFitnessData.getCalories();
    Long requestUserCalories = requestedUserFitnessData.getCalories();

    if (dbUserSteps < requestUserSteps) {
      dbUserFitnessData.setSteps(requestUserSteps);
      Long pointsToBeAdded = (requestUserSteps - dbUserSteps)/10;
      createActivityForFitnessData(requestedUserFitnessData.getHwAddress(),
          dbUserFitnessData.getId(),
          ActivityType.STEPS,
          requestUserSteps - dbUserSteps,
          pointsToBeAdded);
    }

    if (dbUserCalories < requestUserCalories) {
      dbUserFitnessData.setCalories(requestUserCalories);
      Long pointsToBeAdded = (requestUserCalories - dbUserCalories)/5;
      createActivityForFitnessData(requestedUserFitnessData.getHwAddress(),
          dbUserFitnessData.getId(),
          ActivityType.CALORIES,
          requestUserCalories - dbUserCalories,
          pointsToBeAdded);
    }

    return userFitnessDataRepository.save(dbUserFitnessData);
  }

  private void createActivityForFitnessData(String hwAddress,
                                            UUID taskId,
                                            ActivityType activityType,
                                            Long count,
                                            Long points) {

    Activity activity = new Activity();
    activity.setId(UUID.randomUUID());
    activity.setTaskId(taskId);
    activity.setActivityType(activityType);
    activity.setTime(Long.parseLong("0"));
    activity.setPoints(points);
    activity.setDate(new Date().getTime());
    GUser user = getUserByHwAddress(hwAddress);
    user.setPoints( user.getPoints() + points);
    activity.setUser(user);

    activityRepository.save(activity);
    System.out.println("Saved Activity");
  }

  private GUser getUserByHwAddress (String hwAddress) {
    return userRepository.findByHwAddress(hwAddress)
        .orElseThrow(() -> new RuntimeException("hwAddress is not present in DB"));
  }

}
