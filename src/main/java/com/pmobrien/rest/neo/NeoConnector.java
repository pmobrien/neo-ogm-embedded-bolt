package com.pmobrien.rest.neo;

import com.google.common.base.Strings;
import com.google.common.base.Suppliers;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class NeoConnector {
  
  private static final String NEO_STORE = "neo-store";
  private static final String NEO_DRIVER = EmbeddedDriver.class.getName();
  
  private static final NeoConnector INSTANCE = new NeoConnector();
  private static final Supplier<SessionFactory> SESSION_FACTORY = Suppliers.memoize(() -> initializeSessionFactory());
  
  private NeoConnector() {}
  
  public static NeoConnector getInstance() {
    return INSTANCE;
  }
  
  public void sessionOperation(Consumer<Session> function) {
    Session session = SESSION_FACTORY.get().openSession();
    function.accept(session);
    session.clear();
  }
  
  public <T> T returningSessionOperation(Function<Session, T> function) {
    Session session = SESSION_FACTORY.get().openSession();
    T result = function.apply(session);
    
    session.clear();
    
    return result;
  }
  
  private static SessionFactory initializeSessionFactory() {
    Configuration configuration = new Configuration();
    configuration.driverConfiguration()
        .setURI(uri())
        .setDriverClassName(driver());

    return new SessionFactory(configuration, "com.pmobrien.rest.pojo");
  }

  private static String uri() {
    if(Strings.isNullOrEmpty(System.getProperty(NEO_STORE))) {
      throw new RuntimeException(String.format("%s property must be set.", NEO_STORE));
    }
    
    return System.getProperty(NEO_STORE);
  }

  private static String driver() {
    return NEO_DRIVER;
  }
}
