package com.pmobrien.vultus.liftoff.services.impl;

import com.google.common.base.Strings;
import com.pmobrien.vultus.liftoff.accessors.ScoresAccessor;
import com.pmobrien.vultus.liftoff.exceptions.ValidationException;
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
  public Response addScore(String password, AddScoreRequest request) {
    if(Strings.isNullOrEmpty(password)) {
      throw new ValidationException("Error: Password is required.");
    }
    
    if(!"vultus".equals(password)) {
      throw new ValidationException("Error: Password is incorrect.");
    }
    
    if(Strings.isNullOrEmpty(request.getFirstName()) || Strings.isNullOrEmpty(request.getLastName())) {
      throw new ValidationException("Error: First and last name are required.");
    }
    
    return Response.ok(new ScoresAccessor().addScore(request.toAthlete())).build();
  }
}
