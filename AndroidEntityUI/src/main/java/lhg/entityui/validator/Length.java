package lhg.entityui.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * The annotated element size must be between the specified boundaries (included).
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 *     <li>{@code Collection} (collection size is evaluated)</li>
 *     <li>{@code Map} (map size is evaluated)</li>
 *     <li>Array (array length is evaluated)</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {Length.MyConstraintValidator.class})
public @interface Length {
    int min() default 0;
    int max() default Integer.MAX_VALUE;

    String message() default "长度范围错误";

    class MyConstraintValidator extends ConstraintValidator<Length, Object> {

        int min , max;
        @Override
        public void initialize(Length constraintAnnotation) {
            setMessage(constraintAnnotation.message());
            min = constraintAnnotation.min();
            max = constraintAnnotation.max();
        }

        @Override
        public boolean isValid(Object value) {
            int size = 0;
            if (value == null) {
            }
            else if (value instanceof Collection) {
                size = ((Collection)value).size();
            }
            else if (value instanceof Map) {
                size = ((Map)value).size();
            }
            else if (value.getClass().isArray()) {
                size = Array.getLength(value);
            }
            else if (value instanceof Number) {
                size = String.valueOf(value).length();
            }
            else if (value instanceof CharSequence) {
                size = String.valueOf(value).length();
            }
            return size >= min && size <= max;
        }
    }
}
