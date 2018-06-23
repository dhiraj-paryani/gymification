package com.neo.gymification.models;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
  @NotBlank
  private String userName;

  @NotBlank
  private String password;
}
