package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.ScoreNode;
import com.pmobrien.vultus.liftoff.services.pojo.AddScoreRequest;

public class ScoresAccessor {

  public ScoreNode addScore(AddScoreRequest addScoreRequest) {
    return Sessions.returningSessionOperation(session -> {
      ScoreNode score = addScoreRequest.toScoreNode();
      session.save(score);
      
      return session.load(ScoreNode.class, score.getId());
    });
  }
}
