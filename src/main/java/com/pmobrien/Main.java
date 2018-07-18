package com.pmobrien;

import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.StorageResource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  
  private static final ExecutorService POOL = Executors.newFixedThreadPool(5);

  public static void main(String[] args) {
    StorageResource base = NeoEntity.create(StorageResource.class)
        .setName("storage:/base");
    
    Sessions.sessionOperation(session -> {
      session.save(base);
    });
    
    for(int i = 0; i < 100; ++i) {
      POOL.submit(() -> Sessions.sessionOperation(session -> {
        session.save(NeoEntity.create(StorageResource.class).setName("filename"));
      }));
    }
    
    while(true) {}
  }
}
