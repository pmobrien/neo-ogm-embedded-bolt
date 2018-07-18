package com.pmobrien.neo.pojo;

import java.util.Date;
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
  
  private boolean dir;
  
  private Date created;
  private Date lastModified;
  private Long length;
  private String hash;
  
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

  public boolean isDir() {
    return dir;
  }

  public StorageResource setDir(boolean dir) {
    this.dir = dir;
    return this;
  }

  public Date getCreated() {
    return created;
  }

  public StorageResource setCreated(Date created) {
    this.created = created;
    return this;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public StorageResource setLastModified(Date lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  public Long getLength() {
    return length;
  }

  public StorageResource setLength(Long length) {
    this.length = length;
    return this;
  }

  public String getHash() {
    return hash;
  }

  public StorageResource setHash(String hash) {
    this.hash = hash;
    return this;
  }
}
