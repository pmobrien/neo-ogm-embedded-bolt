package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.exceptions.ValidationException;
import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import com.pmobrien.vultus.liftoff.services.pojo.CalculatedScore;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;

public class ScoresAccessor {

  public Collection<CalculatedScore> getScores(Athlete.AgeGroup ageGroup, Athlete.Gender gender) {
    Filters filters = new Filters();
    
    if(ageGroup != null) {
      filters.add(new Filter("ageGroup", ComparisonOperator.EQUALS, ageGroup));
    }
    
    if(gender != null) {
      Filter filter = new Filter("gender", ComparisonOperator.EQUALS, gender);
      
      if(ageGroup != null) {
        filter.setBooleanOperator(BooleanOperator.AND);
      }
      
      filters.add(filter);
    }
    
    return Sessions.returningSessionOperation(session -> session.loadAll(Athlete.class, filters))
        .stream()
        .map(athlete -> Calculator.getScore(athlete))
        .sorted(Comparator.<CalculatedScore>comparingDouble(score -> score.getScore()).reversed())
        .collect(Collectors.toList());
  }
  
  public Athlete addScore(Athlete athlete) {
    return Sessions.returningSessionOperation(session -> {
      Athlete athleteByUsername =
          session.loadAll(Athlete.class, new Filter("username", ComparisonOperator.EQUALS, athlete.getUsername()))
              .stream()
              .findFirst()
              .orElse(null);
      
      if(athleteByUsername == null) {
        if(athlete.getWeight() == null) {
          throw new ValidationException("Error: You have not entered a weight yet.");
        }
        
        if(athlete.getGender() == null) {
          throw new ValidationException("Error: You have not entered a gender yet.");
        }
        
        if(athlete.getAgeGroup()== null) {
          throw new ValidationException("Error: You have not entered an age yet.");
        }
        
        session.save(athlete);
        
        return session.load(Athlete.class, athlete.getId());
      } else {
        session.save(
            athleteByUsername
                .setWeight(Optional.ofNullable(athlete.getWeight()).orElse(athleteByUsername.getWeight()))
                .setGender(Optional.ofNullable(athlete.getGender()).orElse(athleteByUsername.getGender()))
                .setAgeGroup(Optional.ofNullable(athlete.getAgeGroup()).orElse(athleteByUsername.getAgeGroup()))
                .setSnatch(Optional.ofNullable(athlete.getSnatch()).orElse(athleteByUsername.getSnatch()))
                .setCleanAndJerk(
                    Optional.ofNullable(athlete.getCleanAndJerk()).orElse(athleteByUsername.getCleanAndJerk())
                )
                .setMetcon(Optional.ofNullable(athlete.getMetcon()).orElse(athleteByUsername.getMetcon()))
        );
        
        return session.load(Athlete.class, athleteByUsername.getId());
      }
    });
  }
  
  private static class Calculator {

    private static CalculatedScore getScore(Athlete athlete) {
      return new CalculatedScore()
          .setUsername(athlete.getUsername())
          .setSnatch(athlete.getSnatch())
          .setCleanAndJerk(athlete.getCleanAndJerk())
          .setMetcon(athlete.getMetcon())
          .setScore(calculate(athlete));
    }
    
    private static Double calculate(Athlete athlete) {
      if(athlete.getSnatch() == null || athlete.getCleanAndJerk() == null || athlete.getMetcon() == null) {
        return 0.0;
      }
      
      return new BigDecimal(sinclair(athlete.getSnatch() + athlete.getCleanAndJerk(), athlete.getWeight(), athlete.getGender()) + (double)athlete.getMetcon())
          .setScale(2, BigDecimal.ROUND_HALF_EVEN)
          .doubleValue();
    }
    
    // https://en.wikipedia.org/wiki/Sinclair_Coefficients
    private static Double sinclair(double total, double weight, Athlete.Gender gender) {
      return total * Math.pow(10.0, Sinclair.getA(gender) * Math.pow(Math.log10(weight / Sinclair.getb(gender)), 2));
    }
    
    private static class Sinclair {
      
      private static Double getA(Athlete.Gender gender) {
        return Athlete.Gender.MALE.equals(gender)
            ? 0.794358141
            : 0.897260740;
      }
      
      private static Double getb(Athlete.Gender gender) {
        return Athlete.Gender.MALE.equals(gender)
            ? 383.6646
            : 325.6572;
      }
    }
  }
}
