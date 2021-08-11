package lhg.entityui.validator;

import java.lang.annotation.Annotation;

public abstract class ConstraintValidator<A extends Annotation, T> {
    private String message;

    public String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    public abstract void initialize(A constraintAnnotation);

    public abstract boolean isValid(T value);
}

