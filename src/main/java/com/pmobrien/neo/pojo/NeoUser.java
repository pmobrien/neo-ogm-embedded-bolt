package com.pmobrien.neo.pojo;

import java.util.List;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class NeoUser extends NeoEntity {

  private String name;
  
  @Relationship(type = "HAS_ACCESS_TO", direction = Relationship.OUTGOING)
  private List<Share> shares;
  
  @Relationship(type = "SUPERVISED_BY", direction = Relationship.INCOMING)
  private List<Share> supervisoryShares;
  
  public NeoUser() {}

  public String getName() {
    return name;
  }

  public NeoUser setName(String name) {
    this.name = name;
    return this;
  }

  public List<Share> getShares() {
    return shares;
  }

  public NeoUser setShares(List<Share> shares) {
    this.shares = shares;
    return this;
  }

  public List<Share> getSupervisoryShares() {
    return supervisoryShares;
  }

  public NeoUser setSupervisoryShares(List<Share> supervisoryShares) {
    this.supervisoryShares = supervisoryShares;
    return this;
  }
}
