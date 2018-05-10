package com.pmobrien.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class NeoUser extends NeoEntity {

  private String name;
  
  public NeoUser() {}

  public String getName() {
    return name;
  }

  public NeoUser setName(String name) {
    this.name = name;
    return this;
  }
}
