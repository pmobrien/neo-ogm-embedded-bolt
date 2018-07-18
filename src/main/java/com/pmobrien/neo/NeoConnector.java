package com.pmobrien.neo;

import com.google.common.base.Suppliers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.service.Components;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class NeoConnector {
  
  private static final String POJO_PACKAGE = "com.pmobrien.neo.pojo";
  
  private static final NeoConnector INSTANCE = new NeoConnector();
  private static final Supplier<SessionFactory> SESSION_FACTORY = Suppliers.memoize(() -> initializeSessionFactory());
  
  private static final Path NEO_STORE = Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "neo-store");
  
  private NeoConnector() {}
  
  public static NeoConnector getInstance() {
    return INSTANCE;
  }
  
  protected Session newSession() {
    return SESSION_FACTORY.get().openSession();
  }
  
  private static SessionFactory initializeSessionFactory() {
    GraphDatabaseSettings.BoltConnector bolt = new GraphDatabaseSettings.BoltConnector("0");
    
    Driver driver = new EmbeddedDriver(
        new GraphDatabaseFactory()
            .newEmbeddedDatabaseBuilder(NEO_STORE.toFile())
            .setConfig(bolt.type, GraphDatabaseSettings.Connector.ConnectorType.BOLT.name())
            .setConfig(bolt.enabled, "true")
            .setConfig(bolt.listen_address, "localhost:17688")
            .setConfig(bolt.advertised_address, "localhost:17688")
            .setConfig(bolt.encryption_level, GraphDatabaseSettings.BoltConnector.EncryptionLevel.DISABLED.name())
            .newGraphDatabase()
    );
    
    Components.setDriver(driver);
    
    return new SessionFactory((Configuration)null, POJO_PACKAGE);
  }
}