package com.pmobrien.neo.pojo;

import java.util.List;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class StorageResource extends NeoEntity {

  private String name;
  
  @Relationship(type = "PARENT_OF", direction = Relationship.INCOMING)
  private StorageResource parent;
  
  @Relationship(type = "PARENT_OF", direction = Relationship.OUTGOING)
  private List<StorageResource> children;
  
  public StorageResource() {}

  public String getName() {
    return name;
  }

  public StorageResource setName(String name) {
    this.name = name;
    return this;
  }

  public StorageResource getParent() {
    return parent;
  }

  public StorageResource setParent(StorageResource parent) {
    this.parent = parent;
    return this;
  }

  public List<StorageResource> getChildren() {
    return children;
  }

  public StorageResource setChildren(List<StorageResource> children) {
    this.children = children;
    return this;
  }
}
