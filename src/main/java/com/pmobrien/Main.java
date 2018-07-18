package com.pmobrien;

import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.StorageResource;

public class Main {

  public static void main(String[] args) {
    StorageResource base = NeoEntity.create(StorageResource.class)
        .setName("storage:/base");
    
    Sessions.sessionOperation(session -> {
      session.save(base);
    });
    
    while(true) {}
  }
}
