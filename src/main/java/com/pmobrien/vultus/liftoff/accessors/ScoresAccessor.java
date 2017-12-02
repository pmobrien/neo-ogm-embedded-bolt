package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.ScoreNode;
import java.util.Collection;
import java.util.Optional;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;

public class ScoresAccessor {

  public Collection<ScoreNode> getScores() {
    return Sessions.returningSessionOperation(session -> session.loadAll(ScoreNode.class));
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
}
