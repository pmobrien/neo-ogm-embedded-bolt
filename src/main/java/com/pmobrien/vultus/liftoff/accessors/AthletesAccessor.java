package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.exceptions.ValidationException;
import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import com.pmobrien.vultus.liftoff.services.pojo.CalculatedScore;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;

public class AthletesAccessor {

  public Collection<CalculatedScore> getScores(Athlete.AgeGroup ageGroup, Athlete.Gender gender, Boolean rx) {
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
    
    if(rx != null) {
      if(ageGroup == null && gender == null) {
        filters.add(new Filter("rx", ComparisonOperator.EQUALS, rx));
      } else {
        Filter filter = new Filter("rx", ComparisonOperator.EQUALS, rx);
        filter.setBooleanOperator(BooleanOperator.AND);

        filters.add(filter);
      }
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
        
        session.save(athlete.setUpdated(new Date()));
        
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
                .setRx(Optional.ofNullable(athlete.isRx()).orElse(athleteByUsername.isRx()))
                .setCreated(new Date())
                .setUpdated(new Date())
        );
        
        return session.load(Athlete.class, athleteByUsername.getId());
      }
    });
  }
  
  private static class Calculator {

    private static CalculatedScore getScore(Athlete athlete) {
      double score;
      double sinclair;
      
      DecimalFormat formatter = new DecimalFormat("##.##");
      
      try {
        if(athlete.getSnatch() == null || athlete.getCleanAndJerk() == null) {
          sinclair = 0.0;
        } else {
          sinclair = (double)formatter.parse(
              formatter.format(
                  sinclair(athlete.getSnatch() + athlete.getCleanAndJerk(), athlete.getWeight(), athlete.getGender())
              )
          );
        }
        
        score = athlete.getMetcon() == null || sinclair == 0.0
            ? 0.0
            : (double)formatter.parse(formatter.format(sinclair + (double)athlete.getMetcon()));
      } catch(ParseException ex) {
        score = 0.0;
        sinclair = 0.0;
        ex.printStackTrace(System.out);
      }
      
      return new CalculatedScore()
          .setUsername(athlete.getUsername())
          .setSnatch(Optional.ofNullable(athlete.getSnatch()).orElse(0L))
          .setCleanAndJerk(Optional.ofNullable(athlete.getCleanAndJerk()).orElse(0L))
          .setLiftTotal(Optional.ofNullable(athlete.getSnatch()).orElse(0L) + Optional.ofNullable(athlete.getCleanAndJerk()).orElse(0L))
          .setSinclair(sinclair)
          .setMetcon(Optional.ofNullable(athlete.getMetcon()).orElse(0L))
          .setRx(athlete.isRx())
          .setScore(score);
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
