package lhg.entityui.fieldview;

import android.content.Context;
import android.view.View;

import lhg.entityui.annotation.ValueWrapper;
import lhg.entityui.validator.ConstraintValidator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class FieldViewImpl {
    protected final static List<Class> FLOAT_CLASS_LIST = Arrays.asList(double.class, float.class, Double.class, Float.class);
    protected final static List<Class> INT_CLASS_LIST = Arrays.asList(int.class, long.class, byte.class,
            Integer.class, Long.class, Byte.class);

    public ValueWrapper valueWrapper;
    public String name;
    public View view;
    public Object entity;
    public Field field;
    public boolean editable;
    public boolean valueAlignRight = false;
    public List<ConstraintValidator> validators;

    public View createView(Context context) {
        this.view = onCreateView(context);
        return view;
    }

    public void showInvalidError(String error) {

    }

    public abstract View onCreateView(Context context);
}