package com.pmobrien.vultus.liftoff.services.impl;

import com.pmobrien.vultus.liftoff.accessors.ScoresAccessor;
import com.pmobrien.vultus.liftoff.neo.pojo.ScoreNode;
import com.pmobrien.vultus.liftoff.services.IScoresService;
import javax.ws.rs.core.Response;

public class ScoresService implements IScoresService {

  @Override
  public Response getScores(ScoreNode.AgeGroup ageGroup) {
    return Response.ok(new ScoresAccessor().getScores(ageGroup)).build();
  }
  
  @Override
  public Response addScore(ScoreNode score) {
    return Response.ok(new ScoresAccessor().addScore(score)).build();
  }
}
