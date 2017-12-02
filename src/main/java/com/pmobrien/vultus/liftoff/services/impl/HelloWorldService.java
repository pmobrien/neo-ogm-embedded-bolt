package com.pmobrien.vultus.liftoff.services.impl;

import com.pmobrien.vultus.liftoff.services.IHelloWorldService;
import javax.ws.rs.core.Response;

public class HelloWorldService implements IHelloWorldService {

  @Override
  public Response helloWorld() {
    return Response.ok("Hello World").build();
  }
}
