package lhg.entityui.annotation;

import lhg.entityui.fieldview.DateViewImpl;
import lhg.entityui.fieldview.FieldViewImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@FieldView(value = DateView.Creator.class)
public @interface DateView {
    boolean bdate() default true;
    boolean btime() default true;
    String format() default "yyyy-MM-dd HH:mm:ss";

    class Creator implements FieldViewCreator<DateView> {

        @Override
        public FieldViewImpl create(DateView ann, Map<String, Object> props) {
            DateViewImpl impl = new DateViewImpl();
            impl.bdate = ann.bdate();
            impl.btime = ann.btime();
            impl.inFormat = ann.format();

            return impl;
        }
    }
}