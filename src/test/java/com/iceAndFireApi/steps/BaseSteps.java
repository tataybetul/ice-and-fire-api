package com.iceAndFireApi.steps;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iceAndFireApi.client.http.IceAndFireApiClient;
import com.iceAndFireApi.utils.ObjectMapperSingleton;
import com.iceAndFireApi.utils.enums.TestContext;
import io.restassured.internal.mapping.Jackson2Mapper;

public class BaseSteps {

    public final Jackson2Mapper jackson2Mapper;
    public final IceAndFireApiClient iceAndFireApiClient;

    public BaseSteps() {
        this.jackson2Mapper = getJackson2Mapper();
        this.iceAndFireApiClient = new IceAndFireApiClient(jackson2Mapper);
    }

    private Jackson2Mapper getJackson2Mapper() {
        return new Jackson2Mapper((type, s) -> {
            ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper;
        });
    }

    public static TestContext testContext() {
        return TestContext.CONTEXT;
    }
}
