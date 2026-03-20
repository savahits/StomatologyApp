package ru.shmelev.stomatologyapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.shmelev.stomatologyapp.utils.PhoneUtils;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        try {
            PhoneUtils.normalize(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}