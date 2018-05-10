package com.pmobrien;

import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.StorageResource;

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
      StorageResource resource = session.load(StorageResource.class, file.getUuid());
      
      System.out.println("Only loads one level of relationships so only get one parent here (as expected).");
      System.out.print(String.format("(%s)", resource.getName()));
      
      while(resource.getParent() != null) {
        System.out.print(String.format("<-[PARENT_OF]-(%s)", resource.getParent().getName()));
        resource = resource.getParent();
      }
    });
    
    while(true) {}
  }
}
