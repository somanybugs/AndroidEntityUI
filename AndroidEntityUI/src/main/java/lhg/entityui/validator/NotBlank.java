package lhg.entityui.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字符串
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {NotBlank.MyConstraintValidator.class})
public @interface NotBlank {
    String message() default "不能为空";

    class MyConstraintValidator extends ConstraintValidator<NotBlank, CharSequence> {

        @Override
        public void initialize(NotBlank constraintAnnotation) {
            setMessage(constraintAnnotation.message());
        }

        @Override
        public boolean isValid(CharSequence value) {
            return !(value == null || String.valueOf(value).trim().isEmpty());
        }
    }
}
