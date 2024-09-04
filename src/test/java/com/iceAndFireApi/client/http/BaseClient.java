package com.iceAndFireApi.client.http;

import com.iceAndFireApi.utils.constants.HostConstants;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class BaseClient {

  protected Jackson2Mapper jackson2Mapper;

  public BaseClient(Jackson2Mapper jackson2Mapper) {
    this.jackson2Mapper = jackson2Mapper;
  }

  protected ValidatableResponse sendGetRequest(String endpoint) {
    return given()
            .baseUri(HostConstants.ICE_AND_FIRE_API)
            .when()
            .get(endpoint)
            .then();
  }
}
