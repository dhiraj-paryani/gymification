package com.neo.gymification.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class WeekId implements Serializable{
  Long weekId;

  TaskType taskType;

  public WeekId(Long weekId, TaskType taskType) {
    this.weekId = weekId;
    this.taskType = taskType;
  }

  public WeekId() {

  }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    WeekId weekId = (WeekId) o;

    if (this.weekId != null ? !this.weekId.equals(weekId.weekId) : weekId.weekId != null) return false;
    return taskType == weekId.taskType;
  }

  @Override
  public int hashCode() {
    int result = weekId != null ? weekId.hashCode() : 0;
    result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
    return result;
  }
}
