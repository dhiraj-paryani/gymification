package com.neo.gymification.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.neo.gymification.models.WeeklyTask;
import com.neo.gymification.services.WeeklyTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/task")
public class WeeklyTaskController {

  WeeklyTaskService taskService;

  @Autowired
  public WeeklyTaskController(WeeklyTaskService taskService) {
    this.taskService = taskService;
  }

  @RequestMapping(method = POST)
  @ResponseBody
  public WeeklyTask createTask(@RequestBody WeeklyTask task) {
    return taskService.createWeeklyTask(task);
  }
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public List<WeeklyTask> getAllTaskForCurrentWeek(@RequestParam("hwAddress") String hwAddress) {
    return taskService.getAllTaskForCurrentWeek(hwAddress);
  }
}
