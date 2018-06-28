package com.neo.gymification.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class UserFitnessData {

  @Id
  private UUID id;

  @NotBlank
  private String hwAddress;

  @NonNull
  private Long date;

  @NonNull
  private Long steps;

  @NonNull
  private Long calories;

}
