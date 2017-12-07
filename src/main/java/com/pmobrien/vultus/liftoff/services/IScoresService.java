package com.pmobrien.vultus.liftoff.services;

import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import com.pmobrien.vultus.liftoff.services.requests.AddScoreRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/scores")
public interface IScoresService {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getScores(
      @QueryParam("ageGroup") Athlete.AgeGroup ageGroup,
      @QueryParam("gender") Athlete.Gender gender,
      @QueryParam("rx") Boolean rx
  );
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addScore(
      @QueryParam("password") String password,
      AddScoreRequest request
  );
}
