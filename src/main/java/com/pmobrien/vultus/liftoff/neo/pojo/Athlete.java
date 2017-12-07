package com.pmobrien.vultus.liftoff.neo.pojo;

import java.util.Date;
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
    
    public static AgeGroup fromAge(Long age) {
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
  
  private String username;
  private Long weight;
  private Gender gender;
  private AgeGroup ageGroup;
  private Long snatch;
  private Long cleanAndJerk;
  private Long metcon;
  private Boolean rx = true;
  private Date created;
  private Date updated;

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

  public Boolean isRx() {
    return rx;
  }

  public Athlete setRx(Boolean rx) {
    this.rx = rx;
    return this;
  }

  public Date getCreated() {
    return created;
  }

  public Athlete setCreated(Date created) {
    this.created = created;
    return this;
  }

  public Date getUpdated() {
    return updated;
  }

  public Athlete setUpdated(Date updated) {
    this.updated = updated;
    return this;
  }
}
