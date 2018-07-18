package com.pmobrien.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class StorageResourceFile extends StorageResource {

  public StorageResourceFile() {}

  @Override
  public boolean isDir() {
    return false;
  }

  @Override
  public StorageResource setDir(boolean dir) {
    return (StorageResource)super.setDir(false);
  }
}
