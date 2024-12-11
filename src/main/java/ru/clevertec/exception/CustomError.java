package ru.clevertec.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class CustomError {

    private String code;
    private String message;


    public static CustomError of(String code, String message) {
        return new CustomError(code, message);
    }
}
