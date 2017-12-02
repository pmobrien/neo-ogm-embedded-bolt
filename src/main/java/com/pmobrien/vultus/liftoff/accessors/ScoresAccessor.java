package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.Sessions;
import com.pmobrien.vultus.liftoff.neo.pojo.Score;
import com.pmobrien.vultus.liftoff.services.pojo.AddScoreRequest;

public class ScoresAccessor {

  public Score addScore(AddScoreRequest addScoreRequest) {
    return Sessions.returningSessionOperation(session -> {
      Score score = addScoreRequest.toScore();
      session.save(score);
      
      return session.load(Score.class, score.getId());
    });
  }
}
