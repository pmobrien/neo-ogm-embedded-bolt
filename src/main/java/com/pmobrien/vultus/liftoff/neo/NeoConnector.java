package com.pmobrien.vultus.liftoff.neo;

import com.google.common.base.Strings;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class NeoConnector {
  
  public static final String NEO_STORE = "neo-store";
  
  private static final String POJO_PACKAGE = "com.pmobrien.vultus.liftoff.neo.pojo";
  
  private static final NeoConnector INSTANCE = new NeoConnector();
  private static final Supplier<SessionFactory> SESSION_FACTORY = Suppliers.memoize(() -> initializeSessionFactory());
  
  private NeoConnector() {}
  
  public static NeoConnector getInstance() {
    return INSTANCE;
  }
  
  protected Session newSession() {
    return SESSION_FACTORY.get().openSession();
  }
  
  private static SessionFactory initializeSessionFactory() {
    Configuration configuration = new Configuration.Builder()
        .uri(uri())
        .build();

    return new SessionFactory(configuration, POJO_PACKAGE);
  }

  private static String uri() {
    if(Strings.isNullOrEmpty(System.getProperty(NEO_STORE))) {
      throw new RuntimeException(String.format("%s property must be set.", NEO_STORE));
    }
    
    return String.format("file://%s", System.getProperty(NEO_STORE));
  }
}
