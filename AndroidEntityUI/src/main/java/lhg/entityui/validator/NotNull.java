package lhg.entityui.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {NotNull.MyConstraintValidator.class})
public @interface NotNull {
    String message() default "不能为空";

    class MyConstraintValidator extends ConstraintValidator<NotNull, Object> {

        @Override
        public void initialize(NotNull constraintAnnotation) {
            setMessage(constraintAnnotation.message());
        }

        @Override
        public boolean isValid(Object value) {
            return value != null;
        }
    }
}