package com.example.ai2api.utils.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserLoginValidator.class)
public @interface UserLogin {

    String message() default "player.login.isBusy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
