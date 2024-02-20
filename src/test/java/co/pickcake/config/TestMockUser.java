package co.pickcake.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = TestMockSecurityContext.class)
public @interface TestMockUser {

    String userName() default "hail-cali";
    String email() default "hail@gmail.com";
    String userId() default "hail@gmail.com";
    String password() default "";
    String role() default "ROLE_ADMIN";
}
