package com.neo.gymification.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity()
public class WeeklyTask {

  @Id
  private UUID uuid;

  private long weekId;

  private TaskType taskType;

  @NonNull
  private long winCondition;

  private String taskName;

  private long points;

  private long currentProgress;
/*

  @OneToMany(targetEntity = UserBadge.class, mappedBy = "id")
*/
//  private UUID badgeId;
}
