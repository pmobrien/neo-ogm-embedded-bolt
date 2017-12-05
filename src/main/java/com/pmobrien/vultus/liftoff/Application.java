package com.pmobrien.vultus.liftoff;

import com.google.common.base.Strings;
import com.pobrien.vultus.liftoff.filters.RequestLoggerFilter;
import com.pmobrien.vultus.liftoff.mappers.DefaultObjectMapper;
import com.pmobrien.vultus.liftoff.mappers.UncaughtExceptionMapper;
import com.pmobrien.vultus.liftoff.services.impl.ScoresService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.eclipse.jetty.server.ForwardedRequestCustomizer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Application {
  
  private static final String HTTP_PORT = "http-port";
  private static final String HTTPS_PORT = "https-port";
  private static final String DEFAULT_HTTP_PORT = "80";
  private static final String DEFAULT_HTTPS_PORT = "443";
  
  private static final String KEY_STORE_PATH = "key-store-path";
  private static final String KEY_STORE_PASSWORD = "key-store-password";
  
  private static final String WEBAPP_RESOURCE_PATH = "/com/pmobrien/vultus/liftoff/webapp";
  private static final String INDEX_HTML_PATH = String.format("%s/index.html", WEBAPP_RESOURCE_PATH);

  public static void main(String[] args) {
    try {
      new Application().run(new Server());
    } catch(Throwable t) {
      t.printStackTrace(System.out);
    }
  }
  
  private static int httpPort() {
    return Integer.parseInt(Optional.ofNullable(System.getProperty(HTTP_PORT)).orElse(DEFAULT_HTTP_PORT));
  }
  
  private static boolean useHttps() {
    return !Strings.isNullOrEmpty(System.getProperty(HTTPS_PORT));
  }
  
  private static int httpsPort() {
    return Integer.parseInt(Optional.ofNullable(System.getProperty(HTTPS_PORT)).orElse(DEFAULT_HTTPS_PORT));
  }
  
  private void run(Server server) {
    try {
      server.setHandler(configureHandlers());
      
      if(useHttps()) {
        if(Strings.isNullOrEmpty(System.getProperty(KEY_STORE_PATH))) {
          throw new RuntimeException(String.format("'%s' property must be set to use https.", KEY_STORE_PATH));
        }
        
        if(Strings.isNullOrEmpty(System.getProperty(KEY_STORE_PASSWORD))) {
          throw new RuntimeException(String.format("'%s' property must be set to use https.", KEY_STORE_PASSWORD));
        }
        
        server.addConnector(configureHttpsConnector(server));
      }
      
      server.start();
      server.join();
    } catch(Exception ex) {
      ex.printStackTrace(System.out);
    } finally {
      server.destroy();
    }
  }
  
  private ServerConnector configureHttpsConnector(Server server) {
    HttpConfiguration config = new HttpConfiguration();
    config.addCustomizer(new SecureRequestCustomizer());
    config.addCustomizer(new ForwardedRequestCustomizer());
    
    HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(config);
    ServerConnector httpConnector = new ServerConnector(server, httpConnectionFactory);
    httpConnector.setPort(httpPort());
    server.addConnector(httpConnector);
    
    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStoreType("PKCS12");
    sslContextFactory.setKeyStorePath(System.getProperty(KEY_STORE_PATH));
    sslContextFactory.setKeyStorePassword(System.getProperty(KEY_STORE_PASSWORD));
    sslContextFactory.setKeyManagerPassword(System.getProperty(KEY_STORE_PASSWORD));
    
    ServerConnector connector = new ServerConnector(server, sslContextFactory, httpConnectionFactory);
    connector.setPort(httpsPort());
    
    return connector;
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
                this.getClass().getResource(INDEX_HTML_PATH).toURI().toASCIIString().replaceFirst("/index.html$", "/")
            )
        )
    );
    
    handler.setWelcomeFiles(new String[] { "index.html" });
    handler.addServlet(DefaultServlet.class, "/");
    
    return handler;
  }
}
