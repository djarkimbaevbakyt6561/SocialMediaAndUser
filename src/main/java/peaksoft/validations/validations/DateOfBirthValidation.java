package peaksoft.validations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.validations.validators.DateOfBirthValidator;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = DateOfBirthValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirthValidation {
    String message() default "{You cannot enter because you are under 16 years old!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
