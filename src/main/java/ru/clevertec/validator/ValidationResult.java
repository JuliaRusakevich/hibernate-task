package ru.clevertec.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.clevertec.exception.CustomError;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class ValidationResult {

    private List<CustomError> errors;

    public void add(CustomError error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
