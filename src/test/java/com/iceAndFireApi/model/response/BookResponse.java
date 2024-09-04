package com.iceAndFireApi.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResponse {

    private String url;
    private String name;
    private String isbn;
    private List<String> authors;
    private Integer numberOfPages;
    private String publisher;
    private String country;
    private String mediaType;
    private String released;
    private List<String> characters;
    private List<String> povCharacters;

    public BookResponse() {
    }
}
