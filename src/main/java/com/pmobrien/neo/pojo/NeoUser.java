package com.pmobrien.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class NeoUser extends NeoEntity {

  private String name;
  
  public NeoUser(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public NeoUser setName(String name) {
    this.name = name;
    return this;
  }
}
