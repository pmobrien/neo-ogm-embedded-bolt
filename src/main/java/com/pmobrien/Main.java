package com.pmobrien;

import com.google.common.collect.Maps;
import com.pmobrien.neo.Sessions;
import com.pmobrien.neo.pojo.NeoEntity;
import com.pmobrien.neo.pojo.NeoUser;

public class Main {

  public static void main(String[] args) {
    Sessions.sessionOperation(session -> {
      session.query("match (n) detach delete n", Maps.newHashMap());
      
      session.save(NeoEntity.create(NeoUser.class).setName("Patrick"));
    });
    
    while(true) {}
  }
}
