package com.finder.mypet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERID_DUPLICATED(HttpStatus.BAD_REQUEST, "UserId is duplicated."),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "Nickname is duplicated."),
    USERID_NOTFOUND(HttpStatus.NOT_FOUND, "UserId is not found."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Password does not match");

    private HttpStatus httpStatus;
    private String message;

}
