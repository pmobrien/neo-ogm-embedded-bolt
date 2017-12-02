package com.pmobrien.vultus.liftoff.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Athlete extends NeoEntity {

  public enum Gender {
    MALE,
    FEMALE;
  }
  
  public enum AgeGroup {
    GROUP_0_39,
    GROUP_40_54,
    GROUP_55_PLUS;
  }
  
  private String username;
  private Long weight;
  private Gender gender;
  private AgeGroup ageGroup;
  private Long snatch;
  private Long cleanAndJerk;
  private Long metcon;

  public String getUsername() {
    return username;
  }

  public Athlete setUsername(String username) {
    this.username = username;
    return this;
  }

  public Long getWeight() {
    return weight;
  }

  public Athlete setWeight(Long weight) {
    this.weight = weight;
    return this;
  }

  public Gender getGender() {
    return gender;
  }

  public Athlete setGender(Gender gender) {
    this.gender = gender;
    return this;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public Athlete setAgeGroup(AgeGroup ageGroup) {
    this.ageGroup = ageGroup;
    return this;
  }

  public Long getSnatch() {
    return snatch;
  }

  public Athlete setSnatch(Long snatch) {
    this.snatch = snatch;
    return this;
  }

  public Long getCleanAndJerk() {
    return cleanAndJerk;
  }

  public Athlete setCleanAndJerk(Long cleanAndJerk) {
    this.cleanAndJerk = cleanAndJerk;
    return this;
  }

  public Long getMetcon() {
    return metcon;
  }

  public Athlete setMetcon(Long metcon) {
    this.metcon = metcon;
    return this;
  }
}
