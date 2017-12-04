package com.pmobrien.vultus.liftoff.services.impl;

import com.pmobrien.vultus.liftoff.accessors.ScoresAccessor;
import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import com.pmobrien.vultus.liftoff.services.IScoresService;
import com.pmobrien.vultus.liftoff.services.requests.AddScoreRequest;
import javax.ws.rs.core.Response;

public class ScoresService implements IScoresService {

  @Override
  public Response getScores(Athlete.AgeGroup ageGroup, Athlete.Gender gender) {
    return Response.ok(new ScoresAccessor().getScores(ageGroup, gender)).build();
  }
  
  @Override
  public Response addScore(AddScoreRequest request) {
    return Response.ok(new ScoresAccessor().addScore(request.toAthlete())).build();
  }
}
