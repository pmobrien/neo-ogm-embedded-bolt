package com.pmobrien.neo.pojo.util;

import java.util.Date;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.neo4j.ogm.typeconversion.DateStringConverter;

public class DateConverter {

  public static String toCypherString(Date date) {
    return new DateStringConverter(DateString.ISO_8601).toGraphProperty(date);
  }
}
