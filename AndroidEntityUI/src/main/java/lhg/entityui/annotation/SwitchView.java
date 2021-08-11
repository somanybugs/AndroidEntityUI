package lhg.entityui.annotation;

import lhg.entityui.fieldview.FieldViewImpl;
import lhg.entityui.fieldview.SwitchViewImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@FieldView(SwitchView.Creator.class)
public @interface SwitchView {

    class Creator implements FieldViewCreator<SwitchView> {

        @Override
        public FieldViewImpl create(SwitchView ann, Map<String, Object> props) {
            return new SwitchViewImpl();
        }
    }
}