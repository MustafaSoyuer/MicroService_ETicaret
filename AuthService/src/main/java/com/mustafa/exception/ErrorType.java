package com.mustafa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(1000,"Sunucuda beklenmeyen hata oluştu, lütfen tekrar deneyiniz",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST_ERROR(1001,"Girilen parametreler hatalıdır. Lütfen düzelterek tekrar deneyiniz",HttpStatus.BAD_REQUEST),
    ERROR_DUPLICATE_USERNAME(2000,"Bu Kullanıcı adı zaten kayıtlıdır. Lütfen değiştirerek tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    ERROR_INVALID_LOGIN_PARAMETER(2001,"Kullanıcı adı ya da şifre hatalıdır. Lütfen düzelterek tekrar deneyiniz.",HttpStatus.BAD_REQUEST)
    ;

    int code;
    String message;
    HttpStatus httpStatus;
}
