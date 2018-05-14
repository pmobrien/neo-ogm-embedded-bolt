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
  
  // This is the logic that I want to get rid of. Rather than having to do this every time, I just want to get a single
  // reference back to the bottom-most StorageResource node, with parents wired up properly.
  private static StorageResource findResource(List<StorageResource> resources) {
    // Not sure what order nodes are returned in the list, but all of the parent/children relationships are wired up
    // properly for each row so just pick the first one and find its root.
    StorageResource resource = resources.get(0);
    while(resource.getParent() != null) {
      resource = resource.getParent();
    }
    
    // Now that we are at the root, traverse down to the lowest child. We aren't querying for any siblings so
    // getChildren().get(0) will always work.
    while(resource.getChildren() != null && !resource.getChildren().isEmpty()) {
      resource = resource.getChildren().get(0);
    }
    
    return resource;
  }
  
  private static void printResource(StorageResource resource) {
    StorageResource current = resource;
    
    System.out.println();
    System.out.println("Path:");
    System.out.print(String.format("(%s)", current.getName()));
      
    while(current.getParent() != null) {
      System.out.print(String.format("<-[PARENT_OF]-(%s)", current.getParent().getName()));
      current = current.getParent();
    }
  }
}
