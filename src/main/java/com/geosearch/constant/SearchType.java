package com.geosearch.constant;

public enum SearchType {
  POSTCODE, ADDRESS;

  public static SearchType fromString(String value) {
    try {
      return SearchType.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Неизвестный тип поиска: " + value);
    }
  }
}
