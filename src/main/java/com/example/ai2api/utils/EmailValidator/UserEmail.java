package com.example.ai2api.utils.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserEmailValidator.class)
public @interface UserEmail {

    String message() default "player.email.isBusy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
