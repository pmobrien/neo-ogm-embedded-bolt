package com.pmobrien.vultus.liftoff.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ScoreNode extends NeoEntity {

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

  public ScoreNode setUsername(String username) {
    this.username = username;
    return this;
  }

  public Long getWeight() {
    return weight;
  }

  public ScoreNode setWeight(Long weight) {
    this.weight = weight;
    return this;
  }

  public Gender getGender() {
    return gender;
  }

  public ScoreNode setGender(Gender gender) {
    this.gender = gender;
    return this;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public ScoreNode setAgeGroup(AgeGroup ageGroup) {
    this.ageGroup = ageGroup;
    return this;
  }

  public Long getSnatch() {
    return snatch;
  }

  public ScoreNode setSnatch(Long snatch) {
    this.snatch = snatch;
    return this;
  }

  public Long getCleanAndJerk() {
    return cleanAndJerk;
  }

  public ScoreNode setCleanAndJerk(Long cleanAndJerk) {
    this.cleanAndJerk = cleanAndJerk;
    return this;
  }

  public Long getMetcon() {
    return metcon;
  }

  public ScoreNode setMetcon(Long metcon) {
    this.metcon = metcon;
    return this;
  }
}
