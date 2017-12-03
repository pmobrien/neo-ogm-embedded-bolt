package com.pmobrien.vultus.liftoff;

import com.pobrien.vultus.liftoff.filters.RequestLoggerFilter;
import com.pmobrien.vultus.liftoff.mappers.DefaultObjectMapper;
import com.pmobrien.vultus.liftoff.mappers.UncaughtExceptionMapper;
import com.pmobrien.vultus.liftoff.services.impl.ScoresService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Application {
  
  private static final String PORT = "port";
  private static final String DEFAULT_PORT = "8080";
  
  private static final String WEBAPP_RESOURCE_PATH = "/com/pmobrien/vultus/liftoff/webapp";
  private static final String INDEX_HTML = String.format("%s/index.html", WEBAPP_RESOURCE_PATH);

  public static void main(String[] args) {
    try {
      new Application().run(new Server(port()));
    } catch(Throwable t) {
      t.printStackTrace(System.out);
    }
  }
  
  private static int port() {
    return Integer.parseInt(Optional.ofNullable(System.getProperty(PORT)).orElse(DEFAULT_PORT));
  }
  
  private void run(Server server) {
    try {
      server.setHandler(configureHandlers());
      
      server.start();
      server.join();
    } catch(Exception ex) {
      ex.printStackTrace(System.out);
    } finally {
      server.destroy();
    }
  }
  
  private HandlerList configureHandlers() throws MalformedURLException, URISyntaxException {
    HandlerList handlers = new HandlerList();
    handlers.setHandlers(
        new Handler[] {
          configureApiHandler(),
          configureStaticHandler()
        }
    );
    
    return handlers;
  }
  
  private ServletContextHandler configureApiHandler() {
    ServletContextHandler handler = new ServletContextHandler();
    handler.setContextPath("/api");
    
    handler.addServlet(
        new ServletHolder(
            new ServletContainer(
                new ResourceConfig()
                    .register(ScoresService.class)
                    .register(DefaultObjectMapper.class)
                    .register(RequestLoggerFilter.class)
                    .register(UncaughtExceptionMapper.class)
            )
        ),
        "/*"
    );
    
    return handler;
  }
  
  private ServletContextHandler configureStaticHandler() throws MalformedURLException, URISyntaxException {
    ServletContextHandler handler = new ServletContextHandler();
    handler.setContextPath("/");
    
    handler.setBaseResource(
        Resource.newResource(
            URI.create(
                this.getClass().getResource(INDEX_HTML).toURI().toASCIIString().replaceFirst("/index.html$", "/")
            )
        )
    );
    
    handler.setWelcomeFiles(new String[] { "index.html" });
    handler.addServlet(DefaultServlet.class, "/");
    
    return handler;
  }
}
