package com.pmobrien.vultus.liftoff.services.impl;

import com.pmobrien.vultus.liftoff.services.IScoresService;
import com.pmobrien.vultus.liftoff.services.pojo.AddScoreRequest;
import javax.ws.rs.core.Response;

public class ScoresService implements IScoresService {

  @Override
  public Response addScore(AddScoreRequest addScoreRequest) {
    return Response.ok().build();
  }
}
