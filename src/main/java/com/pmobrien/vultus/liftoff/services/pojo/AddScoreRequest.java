package com.pmobrien.vultus.liftoff.services.pojo;

import com.pmobrien.vultus.liftoff.neo.pojo.Score;

public class AddScoreRequest {

  private String firstName;
  private String lastName;
  private Long weight;
  private Score.Gender gender;
  private Long snatch;
  private Long cleanAndJerk;
  private Long metcon;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Long getWeight() {
    return weight;
  }

  public void setWeight(Long weight) {
    this.weight = weight;
  }

  public Score.Gender getGender() {
    return gender;
  }

  public void setGender(Score.Gender gender) {
    this.gender = gender;
  }

  public Long getSnatch() {
    return snatch;
  }

  public void setSnatch(Long snatch) {
    this.snatch = snatch;
  }

  public Long getCleanAndJerk() {
    return cleanAndJerk;
  }

  public void setCleanAndJerk(Long cleanAndJerk) {
    this.cleanAndJerk = cleanAndJerk;
  }

  public Long getMetcon() {
    return metcon;
  }

  public void setMetcon(Long metcon) {
    this.metcon = metcon;
  }
}
