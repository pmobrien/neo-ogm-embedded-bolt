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
    
    while(true) {}
  }
}
