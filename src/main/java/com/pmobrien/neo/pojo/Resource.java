package com.pmobrien.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Resource extends NeoEntity {

  private String name;
  
  public Resource() {}
  
  public String getName() {
    return name;
  }

  public Resource setName(String name) {
    this.name = name;
    return this;
  }
}
