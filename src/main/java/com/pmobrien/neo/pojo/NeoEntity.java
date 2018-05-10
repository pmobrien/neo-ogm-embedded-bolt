package com.pmobrien.neo.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pmobrien.neo.pojo.util.UUIDConverter;
import java.util.UUID;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;

@NodeEntity
public abstract class NeoEntity {
  
  @GraphId
  private Long id;

  @Convert(UUIDConverter.class)
  @Index(primary = true, unique = true)
  private UUID uuid;

  public UUID getUuid() {
    return uuid;
  }

  protected void setUuid(UUID uuid) {
    this.uuid = uuid;
  }
  
  public String toJson() {
    try {
      return new ObjectMapper()
          .configure(SerializationFeature.INDENT_OUTPUT, true)
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .writeValueAsString(this);
    } catch(JsonProcessingException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public static <T extends NeoEntity> T create(Class<T> type) {
    try {
      T instance = type.newInstance();
      instance.setUuid(UUID.randomUUID());
      
      return instance;
    } catch(ReflectiveOperationException ex) {
      throw new RuntimeException(String.format("Error creating NeoEntity of type %s.", type), ex);
    }
  }
}
