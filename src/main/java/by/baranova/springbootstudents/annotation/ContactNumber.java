package by.baranova.springbootstudents.annotation;

import by.baranova.springbootstudents.validator.ContactNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContactNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumber {

    String message() default "Номер некорректен";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
