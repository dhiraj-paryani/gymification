package com.neo.gymification.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Entity
public class User {

  @Id
  @NotBlank
  private String userName;

  @NotBlank
  private String password;

  private String name;

  @NotBlank
  private String hwAddress;

  private Integer points;

  /*@OneToMany
  private List<Activity> userActivities;*/
}
