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
  
  public Athlete toAthlete() {
    return new Athlete()
        .setUsername(String.format("%s %s", firstName, lastName))
        .setWeight(weight)
        .setGender(gender)
        .setAgeGroup(ageToAgeGroup(age))
        .setSnatch(snatch)
        .setCleanAndJerk(cleanAndJerk)
        .setMetcon(metcon);
  }
  
  private Athlete.AgeGroup ageToAgeGroup(Long age) {
    if(age == null) {
      return null;
    }
    
    if(age < 40) {
      return Athlete.AgeGroup.GROUP_0_39;
    } else if(age < 55) {
      return Athlete.AgeGroup.GROUP_40_54;
    } else {
      return Athlete.AgeGroup.GROUP_55_PLUS;
    }
  }
}
