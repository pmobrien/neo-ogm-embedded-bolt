package com.pmobrien;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.StorageResource;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    StorageResource base = NeoEntity.create(StorageResource.class)
        .setName("storage:/base");

    StorageResource top = NeoEntity.create(StorageResource.class)
        .setName("top")
        .setParent(base);

    StorageResource folder = NeoEntity.create(StorageResource.class)
        .setName("folder")
        .setParent(top);

    StorageResource file = NeoEntity.create(StorageResource.class)
        .setName("file.txt")
        .setParent(folder);
    
    Sessions.sessionOperation(session -> {
      session.save(file);
    });
    
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("Begin queries:");
    
    Sessions.sessionOperation(session -> {
      System.out.println("Only loads one level of relationships so only get one parent here (as expected).");
      printResource(session.load(StorageResource.class, file.getUuid()));
    });
    
    System.out.println();
    System.out.println();
    System.out.println();
    
    Sessions.sessionOperation(session -> {
      System.out.println("Matches all parents in the query, but the StorageResource object that is returned does not have these references.");
      printResource(
          session.queryForObject(
              StorageResource.class,
              new StringBuilder()
                  .append("MATCH (resource:StorageResource { uuid: {resourceId} })").append(System.lineSeparator())
                  .append("MATCH (resource)<-[:PARENT_OF*]-(:StorageResource)").append(System.lineSeparator())
                  .append("RETURN resource")
                  .toString(),
              ImmutableMap.<String, Object>builder()
                  .put("resourceId", file.getUuid())
                  .build()
          )
      );
    });
    
    System.out.println();
    System.out.println();
    System.out.println();
    
    Sessions.sessionOperation(session -> {
      System.out.println("Returns all StorageResources, but each one is an item in the list, even though they all have parent/children relationships wired up properly.");
      printResource(
          findResource(
              Lists.newArrayList(
                  session.query(
                      StorageResource.class,
                      new StringBuilder()
                          .append("MATCH (resource:StorageResource { uuid: {resourceId} })").append(System.lineSeparator())
                          .append("RETURN (resource)<-[:PARENT_OF*]-(:StorageResource)")
                          .toString(),
                      ImmutableMap.<String, Object>builder()
                          .put("resourceId", file.getUuid())
                          .build()
                  )
              )
          )
      );
    });
    
    System.out.println();
    System.out.println();
    System.out.println();
    
    Sessions.sessionOperation(session -> {
      System.out.println("Expecting only one row here but a list is returned, and none of the parent references are wired up.");
      printResource(
          findResource(
              Lists.newArrayList(
                  session.query(
                      StorageResource.class,
                      new StringBuilder()
                          .append("MATCH path=(:StorageResource)-[:PARENT_OF*]->(child:StorageResource { uuid: {resourceId} }) ").append(System.lineSeparator())
                          .append("WITH nodes(path) as column").append(System.lineSeparator())
                          .append("UNWIND column AS row").append(System.lineSeparator())
                          .append("RETURN row")
                          .toString(),
                      ImmutableMap.<String, Object>builder()
                          .put("resourceId", file.getUuid())
                          .build()
                  )
              )
          )
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
