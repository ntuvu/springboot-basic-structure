package com.tu.hellospring.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User already existed"),
    UNCATEGORIZED(9999, "Something went wrong"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters and less than 20 characters"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
