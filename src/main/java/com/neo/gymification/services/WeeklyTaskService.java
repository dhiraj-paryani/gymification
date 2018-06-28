package com.neo.gymification.services;

import com.neo.gymification.models.Activity;
import com.neo.gymification.models.TaskType;
import com.neo.gymification.models.UserFitnessData;
import com.neo.gymification.models.WeeklyTask;
import com.neo.gymification.repositories.UserFitnessDataRepository;
import com.neo.gymification.repositories.WeeklyTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WeeklyTaskService {

  WeeklyTaskRepository weeklyTaskRepository;
  ActivityServiceImpl activityService;
  UserFitnessDataRepository userFitnessDataRepository;

  public WeeklyTaskService(WeeklyTaskRepository weeklyTaskRepository, ActivityServiceImpl
      activityService, UserFitnessDataRepository userFitnessDataRepository) {
    this.weeklyTaskRepository = weeklyTaskRepository;
    this.activityService = activityService;
    this.userFitnessDataRepository = userFitnessDataRepository;
  }

  public WeeklyTask createWeeklyTask(WeeklyTask task) {
    Date weekDate = new Date(task.getWeekId());
    Calendar cal = Calendar.getInstance();
    cal.setTime(weekDate);
    long weekId = cal.get(Calendar.WEEK_OF_YEAR);
    task.setWeekId(weekId);
    if (weeklyTaskRepository.findByWeekIdAndTaskType(weekId, task.getTaskType()).isPresent()) {
      throw new RuntimeException("Task already exists");
    }
    task.setUuid(UUID.randomUUID());
    return weeklyTaskRepository.save(task);
  }

  public List<WeeklyTask> getAllTaskForCurrentWeek(String hwAddress) {
    Date weekDate = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(weekDate);
    long weekId = cal.get(Calendar.WEEK_OF_YEAR);
    if (!weeklyTaskRepository.findAllByWeekId(weekId).isPresent()) {
      throw new RuntimeException("task not found");
    }
    List<WeeklyTask> tasks = weeklyTaskRepository.findAllByWeekId(weekId).get();
    List<WeeklyTask> newTasks = new ArrayList<WeeklyTask>();
    for(WeeklyTask task: tasks) {
      WeeklyTask t = populateTask(task, hwAddress);
      newTasks.add(t);
      if(t.getWinCondition() <= t.getCurrentProgress()) {
        activityService.assingPointsFromTask(t, hwAddress);
      }
    }
    return newTasks;
  }

  public WeeklyTask populateTask(WeeklyTask task, String hwAddress) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.WEEK_OF_YEAR, (int) task.getWeekId());
    cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
    cal.clear(Calendar.MINUTE);
    cal.clear(Calendar.SECOND);
    cal.clear(Calendar.MILLISECOND);
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
    long startTime = cal.getTimeInMillis();
    List<Activity> activities = activityService.getUserActivitiesByUserNameAfterDate(hwAddress, startTime);
    System.out.println("List of activites found are " + activities.size());
    cal.add(Calendar.DATE, 1);
    long endTime = cal.getTimeInMillis();
    System.out.println("Start of the week" + startTime);

    long currentTime = new Date().getTime();

    if (task.getTaskType() == TaskType.STREAK) {
      long streak = 0;

      while (endTime < currentTime + 24*60*60*1000) {
        long startStreak = streak;
        if(task.getWinCondition() <= streak) {
          break;
        }
        for(Activity activity: activities) {
          System.out.println("Activity date: " + activity.getDate() + " " + " Type " + activity.getActivityType());
          if(activity.getDate() >= startTime && activity.getDate() < endTime){
            System.out.println("Adding streak ");
            streak++;
            break;
          }
        }
        if(startStreak == streak)
          streak = 0;
        startTime = endTime;
        cal.add(Calendar.DATE, 1);
        endTime = cal.getTimeInMillis();
      }
      task.setCurrentProgress(streak);
    } else if (task.getTaskType() == TaskType.TOTAL_TIME) {
      long totalTime = 0;
      for(Activity activity: activities) {
        System.out.println("Add time " + activity.getTime()+  " total " + totalTime);
        totalTime += activity.getTime();
      }
      task.setCurrentProgress(totalTime);
    } else if (task.getTaskType() == TaskType.STEPS) {
      Date todayDate = new Date();
      cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
      Date startDate = cal.getTime();

      long totalSteps = 0;
      while(todayDate.after(startDate) || todayDate.equals(startDate)) {
        Optional<UserFitnessData> maybeData = userFitnessDataRepository.findById(ActivityServiceImpl.dateToDayDate(startDate).toString());

        if(maybeData.isPresent()) {
          totalSteps += maybeData.get().getSteps();
        }
        cal.add(Calendar.DATE, 1);
        startDate = cal.getTime();
      }
      task.setCurrentProgress(totalSteps);

    } else if (task.getTaskType() == TaskType.CALORIES) {
      Date todayDate = new Date();
      cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
      Date startDate = cal.getTime();

      long totalCalories = 0;
      while(todayDate.after(startDate) || todayDate.equals(startDate)) {
        Optional<UserFitnessData> maybeData = userFitnessDataRepository.findById(ActivityServiceImpl.dateToDayDate(startDate).toString());

        if(maybeData.isPresent()) {
          totalCalories += maybeData.get().getCalories();
        }
        cal.add(Calendar.DATE, 1);
        startDate = cal.getTime();
      }

      task.setCurrentProgress(totalCalories);

    }
    return task;
  }
}
