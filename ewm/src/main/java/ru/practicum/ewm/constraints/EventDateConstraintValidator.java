package ru.practicum.ewm.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class EventDateConstraintValidator implements ConstraintValidator<EventDateConstraint, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {
        if (date == null) return false;
        else
            return date.isAfter(LocalDateTime.now().plusHours(2));
    }
}
