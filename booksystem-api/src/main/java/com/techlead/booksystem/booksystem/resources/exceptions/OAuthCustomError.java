package com.techlead.booksystem.booksystem.resources.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class OAuthCustomError {
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
