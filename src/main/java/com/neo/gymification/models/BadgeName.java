package com.neo.gymification.models;

import lombok.Getter;

@Getter
public enum BadgeName {
  attendance_percent_100("attendance_percent_100.png"),
  rank_1("rank_1.jpeg");

  private String fileName;

  private BadgeName(String fileName)
  {
    this.fileName = fileName;
  }
}
