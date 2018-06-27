package com.neo.gymification.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class GUser {

  @Id
  @NotBlank
  private String userName;

  @NotBlank
  private String password;

  private String name;

  @NotBlank
  @Column(unique = true)
  private String hwAddress;

  private Long points = (long)250;

  private boolean active = false;

 @ManyToMany(fetch = FetchType.EAGER)
 private List<UserBadge> userBadges = new ArrayList<>();

 public void addUserBadge(UserBadge userBadge) {
  this.userBadges.add(userBadge);
 }
}
