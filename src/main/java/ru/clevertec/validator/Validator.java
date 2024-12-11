package ru.clevertec.validator;


public interface Validator {

    ValidationResult checkFilterParams(String make, String category, Integer year);


}
