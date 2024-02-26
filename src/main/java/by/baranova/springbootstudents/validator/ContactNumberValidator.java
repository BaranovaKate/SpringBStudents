package by.baranova.springbootstudents.validator;

import by.baranova.springbootstudents.annotation.ContactNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements ConstraintValidator<ContactNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        return value.matches("\\d+")
                && value.length() > 8
                && value.length() < 15;
    }
}
