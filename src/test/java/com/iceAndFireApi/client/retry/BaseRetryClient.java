package com.iceAndFireApi.client.retry;

import dev.failsafe.RetryPolicy;
import com.iceAndFireApi.steps.BaseSteps;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class BaseRetryClient {

    private static final Logger LOGGER = Logger.getLogger(BaseSteps.class.getName());
    public static final int DURATION_MILLISECOND = 1000;
    public static final int MAX_RETRY_COUNT = 3;
    public static final String LOG_PREFIX = "Failure Retrying [%d] | Response: %s";

    private static final List<Integer> httpStatusCodeList = List.of(
            HttpStatus.SC_BAD_REQUEST,
            HttpStatus.SC_NOT_FOUND,
            HttpStatus.SC_INTERNAL_SERVER_ERROR);

    public static RetryPolicy<ValidatableResponse> genericErrorRetryPolicy() {
        return RetryPolicy.<ValidatableResponse>builder()
                .handleResultIf(result ->
                        httpStatusCodeList.contains(result.extract().statusCode()))
                .withDelay(Duration.ofMillis(DURATION_MILLISECOND))
                .withMaxRetries(MAX_RETRY_COUNT)
                .onFailedAttempt(e -> LOGGER.warning(String.format(LOG_PREFIX, e.getAttemptCount(), e.getLastResult())))
                .build();
    }
}