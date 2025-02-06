package com.carpenstreet.external.service;

import com.carpenstreet.external.response.SmsResponse;
import com.carpenstreet.external.response.TranslationResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalService {

    public TranslationResponse translate(String language, String text) {
        String translatedText = text + "(" + language + ")";
        return new TranslationResponse(language, translatedText);
    }

    public SmsResponse sendSms(String phone, String text) {

        if (new Random().nextInt(100) < 50) {
            return new SmsResponse("fail", "존재하지 않는 핸드폰번호입니다.");
        }

        return new SmsResponse("success", "");
    }
}
