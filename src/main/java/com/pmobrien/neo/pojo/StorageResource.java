package com.pmobrien.neo.pojo;

import java.util.List;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class StorageResource extends Resource {
  
  @Relationship(type = "PARENT_OF", direction = Relationship.INCOMING)
  private StorageResource parent;
  
  @Relationship(type = "PARENT_OF", direction = Relationship.OUTGOING)
  private List<StorageResource> children;
  
  @Relationship(type = "LINKS_TO", direction = Relationship.INCOMING)
  private List<Share> shares;
  
  public StorageResource() {}

  @Override
  public StorageResource setName(String name) {
    return (StorageResource)super.setName(name);
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

  public List<Share> getShares() {
    return shares;
  }

  public StorageResource setShares(List<Share> shares) {
    this.shares = shares;
    return this;
  }
}
