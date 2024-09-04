package com.iceAndFireApi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class ObjectMapperSingleton {

  private static ObjectMapper instance;

  private ObjectMapperSingleton() throws IllegalAccessException {
    throw new IllegalAccessException("Util Class!");
  }

  public static ObjectMapper getInstance() {
    if (Objects.nonNull(instance)) {
      return instance;
    }

    instance = new ObjectMapper();

    return instance;
  }
}
