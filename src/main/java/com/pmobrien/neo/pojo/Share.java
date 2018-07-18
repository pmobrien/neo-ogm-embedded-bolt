package com.pmobrien.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Share extends NeoEntity {

  @Relationship(type = "HAS_ACCESS_TO", direction = Relationship.INCOMING)
  private NeoUser user;
  
  @Relationship(type = "LINKS_TO", direction = Relationship.OUTGOING)
  private StorageResource resource;
  
  @Relationship(type = "SUPERVISED_BY", direction = Relationship.OUTGOING)
  private NeoUser supervisedBy;

  public NeoUser getUser() {
    return user;
  }

  public Share setUser(NeoUser user) {
    this.user = user;
    return this;
  }

  public StorageResource getResource() {
    return resource;
  }

  public Share setResource(StorageResource resource) {
    this.resource = resource;
    return this;
  }

  public NeoUser getSupervisedBy() {
    return supervisedBy;
  }

  public Share setSupervisedBy(NeoUser supervisedBy) {
    this.supervisedBy = supervisedBy;
    return this;
  }
}
