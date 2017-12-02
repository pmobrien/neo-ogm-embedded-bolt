package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import com.pmobrien.vultus.liftoff.services.pojo.CalculatedScore;
import java.util.Collection;
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
      
      return (((double)athlete.getSnatch() + (double)athlete.getCleanAndJerk() + (double)athlete.getMetcon()) / 3.0) / (double)athlete.getWeight();
    }
  }
}
