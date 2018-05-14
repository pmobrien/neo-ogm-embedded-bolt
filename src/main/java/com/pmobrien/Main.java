package com.pmobrien;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.StorageResource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.neo4j.ogm.typeconversion.DateStringConverter;


public class Main {

  public static void main(String[] args) {
    Sessions.sessionOperation(session -> {
      // this works
      session.queryForObject(
          StorageResource.class,
          new StringBuilder()
              .append("CREATE (resource:StorageResource { uuid: {uuid}, created: {created} })").append(System.lineSeparator())
              .append("RETURN resource")
              .toString(),
          ImmutableMap.<String, Object>builder()
              .put("uuid", UUID.randomUUID())
              .put("created", new DateStringConverter(DateString.ISO_8601).toGraphProperty(new Date()))
              .build()
      );
      
      // this does not (but probably should...)
      session.queryForObject(
          StorageResource.class,
          new StringBuilder()
              .append("CREATE (resource:StorageResource { uuid: {uuid}, created: {created} })").append(System.lineSeparator())
              .append("RETURN resource")
              .toString(),
          ImmutableMap.<String, Object>builder()
              .put("uuid", UUID.randomUUID())
              .put("created", new Date())
              .build()
      );
    });
    
    while(true) {}
  }
}
