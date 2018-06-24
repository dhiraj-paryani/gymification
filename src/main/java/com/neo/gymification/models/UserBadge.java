package com.neo.gymification.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserBadge {
  @Id
  private UUID id;

  @NonNull
  @Enumerated(EnumType.STRING)
  private BadgeName badgeName;

  private String badgeUrl;
}
