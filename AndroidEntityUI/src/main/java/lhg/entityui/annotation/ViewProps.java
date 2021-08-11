package lhg.entityui.annotation;

import lhg.entityui.DefaultValueWrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewProps {
    int sort() default 0;
    String name() default "";
    boolean editable() default true; //是否可编辑
    Class<? extends DefaultValueWrapper> wrapperClazz() default DefaultValueWrapper.class;
}