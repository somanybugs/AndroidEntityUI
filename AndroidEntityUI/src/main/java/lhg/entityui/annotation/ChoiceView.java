package lhg.entityui.annotation;

import lhg.entityui.fieldview.ChoiceViewImpl;
import lhg.entityui.fieldview.ChoiceViewImplH;
import lhg.entityui.fieldview.ChoiceViewImplV;
import lhg.entityui.fieldview.FieldViewImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@FieldView(value = ChoiceView.Creator.class)
public @interface ChoiceView {
    boolean h() default true; //水平布局
    String[] names() default {};
    String[] values() default {};
    String hint() default "";

    class Creator implements FieldViewCreator<ChoiceView> {

        @Override
        public FieldViewImpl create(ChoiceView ann, Map<String, Object> props) {
            ChoiceViewImpl impl = ann.h()? new ChoiceViewImplH() : new ChoiceViewImplV();
            impl.names = ann.names();
            impl.values = ann.values();
            impl.hint = ann.hint();
            if (props != null) {
                if (props.containsKey("ChoiceView.names")) {
                    impl.names = (String[]) props.get("ChoiceView.names");
                }
                if (props.containsKey("ChoiceView.values")) {
                    impl.values = (String[]) props.get("ChoiceView.values");
                }
            }
            return impl;
        }
    }
}
