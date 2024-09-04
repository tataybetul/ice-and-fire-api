package com.iceAndFireApi.client.http;

import com.iceAndFireApi.utils.constants.EndpointConstants;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.response.ValidatableResponse;

public class IceAndFireApiClient extends BaseClient {

    public IceAndFireApiClient(Jackson2Mapper jackson2Mapper) {
        super(jackson2Mapper);
    }

    public ValidatableResponse getBooksDetail(Long bookId) {
        return sendGetRequest(String.format(EndpointConstants.API_BOOKS_URL, bookId))
                .assertThat();
    }
}
