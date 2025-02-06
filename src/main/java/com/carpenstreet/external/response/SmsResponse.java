package com.carpenstreet.external.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsResponse {
    private String result;
    private String reason;
}
