package ru.clevertec.validator;

import ru.clevertec.exception.CustomError;
import ru.clevertec.util.Constant;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {

    private static final ValidatorImpl INSTANCE = new ValidatorImpl();

    //String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice
    public ValidationResult checkFilterParams(String make, String category, Integer year) {

        ValidationResult validationResult = new ValidationResult();

        //наименование бренда авто - только латинские буквы
        if (isValidString(make)) {
            validationResult.add(new CustomError(
                    make,
                    Constant.ERROR_MESSAGE_INVALID_DATA
            ));
        }

        //наименование категории авто - только латинские буквы
        if (isValidString(category)) {
            validationResult.add(new CustomError(
                    category,
                    Constant.ERROR_MESSAGE_INVALID_DATA
            ));
        }

        //год авто - не больше текущего + 1
        if (!isValidYear(year)) {
            validationResult.add(new CustomError(
                    String.valueOf(year),
                    Constant.ERROR_MESSAGE_INVALID_DATA
            ));
        }
        return validationResult;

    }

    private boolean isValidString(String input) {
        return !Pattern.matches(Constant.PATTERN_FOR_STRING, input);
    }

    private boolean isValidYear(Integer input) {
        return input <= LocalDate.now().getYear() + 1;
    }

    public static ValidatorImpl getInstance() {
        return ValidatorImpl.INSTANCE;
    }
}
