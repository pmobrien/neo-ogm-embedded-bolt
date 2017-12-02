package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.ScoreNode;
import com.pmobrien.vultus.liftoff.services.pojo.CalculatedScore;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;

public class ScoresAccessor {

  public Collection<CalculatedScore> getScores(ScoreNode.AgeGroup ageGroup) {
    Filters filters = new Filters();
    
    if(ageGroup != null) {
      filters.add(new Filter("ageGroup", ComparisonOperator.EQUALS, ageGroup));
    }
    
    return Sessions.returningSessionOperation(session -> session.loadAll(ScoreNode.class, filters))
        .stream()
        .map(score -> Calculator.convert(score))
        .collect(Collectors.toList());
  }
  
  public ScoreNode addScore(ScoreNode score) {
    return Sessions.returningSessionOperation(session -> {
      ScoreNode scoreByUsername =
          session.loadAll(ScoreNode.class, new Filter("username", ComparisonOperator.EQUALS, score.getUsername()))
              .stream()
              .findFirst()
              .orElse(null);
      
      if(scoreByUsername == null) {
        session.save(score);
        
        return session.load(ScoreNode.class, score.getId());
      } else {
        session.save(
            scoreByUsername
                .setWeight(Optional.ofNullable(score.getWeight()).orElse(scoreByUsername.getWeight()))
                .setGender(Optional.ofNullable(score.getGender()).orElse(scoreByUsername.getGender()))
                .setAgeGroup(Optional.ofNullable(score.getAgeGroup()).orElse(scoreByUsername.getAgeGroup()))
                .setSnatch(Optional.ofNullable(score.getSnatch()).orElse(scoreByUsername.getSnatch()))
                .setCleanAndJerk(Optional.ofNullable(score.getCleanAndJerk()).orElse(scoreByUsername.getCleanAndJerk()))
                .setMetcon(Optional.ofNullable(score.getMetcon()).orElse(scoreByUsername.getMetcon()))
        );
        
        return session.load(ScoreNode.class, scoreByUsername.getId());
      }
    });
  }
  
  private static class Calculator {

    private static CalculatedScore convert(ScoreNode score) {
      return new CalculatedScore()
          .setUsername(score.getUsername())
          .setSnatch(score.getSnatch())
          .setCleanAndJerk(score.getCleanAndJerk())
          .setMetcon(score.getMetcon())
          .setScore(calculate(score));
    }
    
    private static Double calculate(ScoreNode score) {
      if(score.getSnatch() == null || score.getCleanAndJerk() == null || score.getMetcon() == null) {
        return 0.0;
      }
      
      return (((double)score.getSnatch() + (double)score.getCleanAndJerk() + (double)score.getMetcon()) / 3.0) / (double)score.getWeight();
    }
  }
}
