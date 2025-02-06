package com.carpenstreet.external.controller;

import com.carpenstreet.external.dto.SmsDto;
import com.carpenstreet.external.dto.TranslationDto;
import com.carpenstreet.external.response.ErrorResponse;
import com.carpenstreet.external.response.SmsResponse;
import com.carpenstreet.external.service.ExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalService externalService;

    private static final int FAILURE_PROBABILITY = 20;
    private static final int DELAY_MS = 3000;

    @PostMapping("/translation")
    public ResponseEntity<Object> translate(@RequestHeader("X-Language") String language,
                                            @RequestBody TranslationDto dto) {

        try {
            return ResponseEntity.ok(externalService.translate(language, dto.text()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal Server Error"));
        }
    }

    @PostMapping("/sms")
    public ResponseEntity<Object> sendSms(@RequestBody SmsDto dto) {

        try {
            SmsResponse response = externalService.sendSms(dto.phone(), dto.text());

            if (response.getResult().equals("fail")) {
                return ResponseEntity.status(200).body(response);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal Server Error"));
        }
    }


    private ResponseEntity<Object> handlePotentialFailures() {

        if (new Random().nextInt(100) < FAILURE_PROBABILITY) {
            log.error("서버 응답 실패");
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal Server Error"));
        }

        try {
            if (new Random().nextInt(100) < FAILURE_PROBABILITY) {
                Thread.sleep(DELAY_MS);
                log.error("서버 응답 지연 실패");
                return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal Server Error"));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return null;
    }
}
