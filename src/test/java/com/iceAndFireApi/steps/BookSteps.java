package com.iceAndFireApi.steps;

import com.iceAndFireApi.client.retry.BaseRetryClient;
import com.iceAndFireApi.client.retry.BookRetryClient;
import com.iceAndFireApi.model.response.BookResponse;
import dev.failsafe.Failsafe;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;

public class BookSteps extends BaseSteps {

    private static final String GENERIC_RETRYABLE_BOOKS_RESPONSE = "genericRetryableBooksResponse";
    private static final Integer maxRetries = 3;

    @Given("get books response with book id:{string}")
    public void getBooksResponseWithBookId(String bookId) {
        ValidatableResponse genericRetryableBooksResponse = getBooksResponseWithGenericRetry(Long.valueOf(bookId));
        testContext().setResponse(GENERIC_RETRYABLE_BOOKS_RESPONSE, genericRetryableBooksResponse);
    }

    private ValidatableResponse getBooksResponseWithGenericRetry(Long bookId) {
        return Failsafe
                .with(BaseRetryClient.genericErrorRetryPolicy())
                .get(() -> iceAndFireApiClient.getBooksDetail(bookId));
    }


    @Given("get books response for simple retry with book id:{string}")
    public void getBooksResponseWithSimpleRetry(String bookId) {
        ValidatableResponse genericRetryableBooksResponse = simpleRetry(Long.valueOf(bookId));
        testContext().setResponse(GENERIC_RETRYABLE_BOOKS_RESPONSE, genericRetryableBooksResponse);
    }

    public ValidatableResponse simpleRetry(Long bookId) {
        ValidatableResponse response = null;
        for (int i = 1; i <= maxRetries; i++) {
            try {
                response = iceAndFireApiClient.getBooksDetail(bookId);
                if (response.extract().statusCode() == HttpStatus.SC_OK) {
                    return response;
                }
                System.out.println("Response:" + response.extract().statusCode() + ", Retry count: " + i);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                if (i == maxRetries) {
                    System.out.println("Maximum number of retries reached. Operation failed.");
                }
            }
        }
        return response;
    }

    @Then("check http status with status code:{string}")
    public void checkHttpStatusWithStatusCode(String statusCode) {
        ValidatableResponse response = testContext().getResponse(GENERIC_RETRYABLE_BOOKS_RESPONSE);
        Assert.assertEquals(Integer.parseInt(statusCode), response.extract().response().getStatusCode());
    }

    @Then("verify book detail with name:{string}, isbn:{string}, numberOfPages:{string} and publisher:{string}")
    public void verifyBookDetail(String name, String isbn, String numberOfPages, String publisher) {
        ValidatableResponse validatableResponse= testContext().getResponse("bookResponse");
        BookResponse bookResponse = validatableResponse.extract().as(BookResponse.class);
        Assert.assertEquals(name, bookResponse.getName());
        Assert.assertEquals(isbn, bookResponse.getIsbn());
        Assert.assertEquals(numberOfPages, bookResponse.getNumberOfPages().toString());
        Assert.assertEquals(publisher, bookResponse.getPublisher());
        Assert.assertFalse(bookResponse.getCharacters().isEmpty());
        Assert.assertFalse(bookResponse.getPovCharacters().isEmpty());
    }

    @Given("get book response with book id:{string} and expected book character:{string}")
    public void getBookResponseWithBookIdAndExpectedBookCharacter(String bookId, String bookCharacter) {
        ValidatableResponse bookResponse = retryableBookResponse(bookId, bookCharacter);
        testContext().setResponse("bookResponse", bookResponse);
    }

    public ValidatableResponse retryableBookResponse(String bookId, String bookCharacter) {
        return Failsafe
                .with(BookRetryClient.bookCharacterRetryPolicy(bookCharacter))
                .get(() -> iceAndFireApiClient.getBooksDetail(Long.valueOf(bookId)));
    }
}
