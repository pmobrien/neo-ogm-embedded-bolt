package com.pmobrien.vultus.liftoff.services.requests;

import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;

public class AddScoreRequest {

  private String firstName;
  private String lastName;
  private Long weight;
  private Athlete.Gender gender;
  private Long age;
  private Long snatch;
  private Long cleanAndJerk;
  private Long metcon;
  private Boolean rx;

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

  public Athlete.Gender getGender() {
    return gender;
  }

  public void setGender(Athlete.Gender gender) {
    this.gender = gender;
  }

  public Long getAge() {
    return age;
  }

  public void setAge(Long age) {
    this.age = age;
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

  public Boolean isRx() {
    return rx;
  }

  public void setRx(Boolean rx) {
    this.rx = rx;
  }
  
  public Athlete toAthlete() {
    return new Athlete()
        .setUsername(String.format("%s %s", firstName.trim(), lastName.trim()))
        .setWeight(weight)
        .setGender(gender)
        .setAgeGroup(Athlete.AgeGroup.fromAge(age))
        .setSnatch(snatch)
        .setCleanAndJerk(cleanAndJerk)
        .setMetcon(metcon)
        .setRx(rx);
  }
}
