package com.pmobrien;

import com.google.common.collect.Lists;
import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.StorageResource;
import com.pmobrien.neo.pojo.util.DateConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  
  private static final ExecutorService POOL = Executors.newFixedThreadPool(5);

  public static void main(String[] args) {
    StorageResource base = NeoEntity.create(StorageResource.class)
        .setDir(true)
        .setName("storage:/base");
    
    Sessions.sessionOperation(session -> {
      session.save(base);
    });
    
    for(int i = 0; i < 100; ++i) {
      POOL.submit(() -> Sessions.sessionOperation(session -> {
        ArrayList<StorageResource> resources = Lists.newArrayList(
            session.query(
                StorageResource.class,
                Queries.SAVE_STORAGE_RESOURCE,
                new HashMap<String, Object>() {{
                  put("parentId", base.getUuid());
                  put("childName", "child.txt");
                  put("created", DateConverter.toCypherString(new Date()));
                  put("childId", UUID.randomUUID());
                  put("dir", false);
                  put("encryption", "ENCRYPTION");
                  put("length", 1024L);
                  put("hash", "ABCDEFG1234");
                }}
            )
        );
        
        if(resources.isEmpty()) {
          // This is what should happen every time, if nothing is returned that means this clause:
          //    WHERE NOT (parent)-[:PARENT_OF]->(:StorageResource { name: {childName} })
          // short circuited the query as expected.
          System.out.println("Could not create StorageResource.");
        } else {
          System.out.println("StorageResource created successfully.");
        }
      }));
    }
    
    while(true) {}
  }
  
  private static final class Queries {
    
    /**
      MATCH (parent:StorageResource { uuid: {parentId} })
      WHERE NOT (parent)-[:PARENT_OF]->(:StorageResource { name: {childName} })
      MERGE(parent)-[:PARENT_OF]->(child:Resource:StorageResource { uuid: {childId}, name: {childName}, created: {created}, lastModified: {created}, dir: {dir} })
      ON CREATE SET child.length = {length}, child.hash = {hash}
      ON CREATE SET parent.lastModified = {created}
      FOREACH (
        x IN CASE
          WHEN child.dir = false
          THEN [1]
        END | SET child :StorageResourceFile
      )
      WITH child
      MATCH path=(child)<-[:PARENT_OF*]-(:StorageResource)
      RETURN nodes(path)
     */
    private static final String SAVE_STORAGE_RESOURCE = new StringBuilder()
        .append("MATCH (parent:StorageResource { uuid: {parentId} })").append(System.lineSeparator())
        .append("WHERE NOT (parent)-[:PARENT_OF]->(:StorageResource { name: {childName} })").append(System.lineSeparator())
        .append("MERGE(parent)-[:PARENT_OF]->(child:Resource:StorageResource { uuid: {childId}, name: {childName}, created: {created}, lastModified: {created}, dir: {dir} })").append(System.lineSeparator())
        .append("ON CREATE SET child.length = {length}, child.hash = {hash}").append(System.lineSeparator())
        .append("ON CREATE SET parent.lastModified = {created}").append(System.lineSeparator())
        .append("FOREACH (").append(System.lineSeparator())
        .append("  x IN CASE").append(System.lineSeparator())
        .append("    WHEN child.dir = false").append(System.lineSeparator())
        .append("    THEN [1]").append(System.lineSeparator())
        .append("  END | SET child :StorageResourceFile").append(System.lineSeparator())
        .append(")").append(System.lineSeparator())
        .append("WITH child").append(System.lineSeparator())
        .append("MATCH path=(child)<-[:PARENT_OF*]-(:StorageResource)").append(System.lineSeparator())
        .append("RETURN nodes(path)")
        .toString();
  }
}
