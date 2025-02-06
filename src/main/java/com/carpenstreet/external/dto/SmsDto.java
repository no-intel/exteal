package com.carpenstreet.external.dto;

public record SmsDto(
        String phone,
        String text
) {
}
