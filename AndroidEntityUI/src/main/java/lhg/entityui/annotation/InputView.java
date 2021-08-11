package lhg.entityui.annotation;

import lhg.entityui.fieldview.FieldViewImpl;
import lhg.entityui.fieldview.InputViewImpl;
import lhg.entityui.fieldview.InputViewImplH;
import lhg.entityui.fieldview.InputViewImplV;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@FieldView(value = InputView.Creator.class)
public @interface InputView {
    String hint() default "";

    boolean h() default true; //水平布局

    int inputType() default -1;

    int enterKey() default Next;

    int WrapLine = 1;
    int Next = 2;

    class Creator implements FieldViewCreator<InputView> {

        @Override
        public FieldViewImpl create(InputView ann, Map<String, Object> props) {
            InputViewImpl impl = ann.h() ? new InputViewImplH() : new InputViewImplV();
            impl.hint = ann.hint();
            impl.inputType = ann.inputType();
            impl.enterKey = ann.enterKey();
            return impl;
        }
    }
}

