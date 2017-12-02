package com.pmobrien.vultus.liftoff.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Score extends NeoEntity {

  public enum Gender {
    MALE,
    FEMALE;
  }
  
  private String username;
  private Long weight;
  private Gender gender;
  private Long snatch;
  private Long cleanAndJerk;
  private Long metcon;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getWeight() {
    return weight;
  }

  public void setWeight(Long weight) {
    this.weight = weight;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
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
  
  public static class Builder {

    private final Score score = new Score();
    
    public Builder username(String username) {
      score.username = username;
      return this;
    }
    
    public Builder weight(Long weight) {
      score.weight = weight;
      return this;
    }
    
    public Builder gender(Gender gender) {
      score.gender = gender;
      return this;
    }
    
    public Builder snatch(Long snatch) {
      score.snatch = snatch;
      return this;
    }
    
    public Builder cleanAndJerk(Long cleanAndJerk) {
      score.cleanAndJerk = cleanAndJerk;
      return this;
    }
    
    public Builder metcon(Long metcon) {
      score.metcon = metcon;
      return this;
    }
    
    public Score build() {
      return score;
    }
  }
}
