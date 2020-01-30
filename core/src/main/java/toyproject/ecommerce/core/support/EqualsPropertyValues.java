package toyproject.ecommerce.core.support;

import org.jvnet.staxex.StAxSOAPBody;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {EqualsPropertyValuesValidator.class})
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface EqualsPropertyValues {

    String message() default "{com.jaesay.support.validator.EqualsPropertyValues.message}";
    Class<?>[] groups() default {};
    Class<? extends StAxSOAPBody.Payload>[] payload() default {};

    String property();
    String comparingProperty();

    @Target({ TYPE, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        EqualsPropertyValues[] values();
    }
}
