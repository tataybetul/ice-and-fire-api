package com.iceAndFireApi.client.retry;

import com.iceAndFireApi.model.response.BookResponse;
import com.iceAndFireApi.steps.BaseSteps;
import dev.failsafe.RetryPolicy;
import io.restassured.response.ValidatableResponse;

import java.time.Duration;
import java.util.logging.Logger;

public class BookRetryClient extends BaseRetryClient {

    private static final Logger LOGGER = Logger.getLogger(BaseSteps.class.getName());

    public static RetryPolicy<ValidatableResponse> bookCharacterRetryPolicy(String character) {
        return RetryPolicy.<ValidatableResponse>builder()
                .handleResultIf(response -> response
                        .extract()
                        .as(BookResponse.class)
                        .getCharacters()
                        .contains(character))
                .withDelay(Duration.ofMillis(DURATION_MILLISECOND))
                .withMaxRetries(MAX_RETRY_COUNT)
                .onFailedAttempt(e -> LOGGER.warning(String.format(LOG_PREFIX, e.getAttemptCount(), e.getLastResult())))
                .build();
    }
}
