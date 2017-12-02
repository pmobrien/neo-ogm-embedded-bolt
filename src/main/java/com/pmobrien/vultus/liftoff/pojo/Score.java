package com.pmobrien.vultus.liftoff.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Score {

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
  
  public String toJson() {
    try {
      return new ObjectMapper()
          .configure(SerializationFeature.INDENT_OUTPUT, true)
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .writeValueAsString(this);
    } catch(JsonProcessingException ex) {
      throw new RuntimeException(ex);
    }
  }
}
