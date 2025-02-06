package com.carpenstreet.external.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TranslationResponse {
    private String language;
    private String text;
}
