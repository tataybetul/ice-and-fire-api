package com.iceAndFireApi.utils.enums;

import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;

public enum TestContext {

    CONTEXT;
    private final ThreadLocal<Map<String, String>> testContexts = withInitial(HashMap::new);
    private final ThreadLocal<Map<String, ValidatableResponse>> testResponseContexts = withInitial(HashMap::new);

    public <T> T getResponse(String name) {
        return (T) testResponseContexts.get()
                .get(name);
    }

    public void setResponse(String name, ValidatableResponse response) {
        testResponseContexts.get().put(name, response);
    }

    public void reset() {
        testContexts.get().clear();
        testResponseContexts.get().clear();
    }
}
