package lhg.entityui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import lhg.entityui.annotation.FieldView;
import lhg.entityui.annotation.ViewProps;
import lhg.entityui.fieldview.FieldViewImpl;
import lhg.entityui.validator.Constraint;
import lhg.entityui.validator.ConstraintValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity2Layout extends LinearLayout {

    List<FieldViewImpl> fieldViewList = new ArrayList<>();
    private List<String> excludeFields;
    private Map<String, Map<String, Object>> fieldDynamicProps;

    public Entity2Layout(Context context) {
        this(context, null);
    }

    public Entity2Layout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Entity2Layout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public void initSmallDetail(Object entity) {
       initSmallDetail(entity, false);
    }

    public void initLargeDetail(Object entity) {
        initLargeDetail(entity, false);
    }

    private void initLargeDetail(Object entity, boolean valueAlignRIght) {
        List<View> views = createViews(entity, false, valueAlignRIght);
        int minHeight = DimenUtils.dip2px(getContext(), 50);
        for (View v : views) {
            v.setMinimumHeight(minHeight);
            v.setOnClickListener(null);
            v.setClickable(false);
        }
        removeAllViews();
        ViewUtils.addItemViewsToLinearLayout(this, views, false, true, 0);
    }
    public void initSmallDetail(Object entity, boolean valueAlignRIght) {
        List<View> views = createViews(entity, false, valueAlignRIght);
        int pad4 = DimenUtils.dip2px(getContext(), 4);
        int minHeight = DimenUtils.dip2px(getContext(), 30);
        for (View v : views) {
            v.setPadding(v.getPaddingLeft(), pad4, v.getPaddingRight(), pad4);
            v.setMinimumHeight(minHeight);
            v.setOnClickListener(null);
            v.setClickable(false);
        }
        removeAllViews();
        ViewUtils.addItemViewsToLinearLayout(this, views, false, true, 0);
    }

    public void initEdit(Object entity) {
        initEdit(entity, false);
    }

    public void initEdit(Object entity, boolean valueALignRight) {
        List<View> views = createViews(entity, true, valueALignRight);
        int minHeight = DimenUtils.dip2px(getContext(), 50);
        for (View v : views) {
            v.setMinimumHeight(minHeight);
        }
        removeAllViews();
        ViewUtils.addItemViewsToLinearLayout(this, views, false, true, 0);
    }

    public List<FieldViewImpl> getFieldViewList() {
        return fieldViewList;
    }

    public Error isEditValid() {
        try {
            for (FieldViewImpl i : fieldViewList) {
                if (i.validators == null) {
                    continue;
                }
                for (ConstraintValidator v : i.validators) {
                    if (!v.isValid(i.field.get(i.entity))) {
                        i.showInvalidError(v.getMessage());
                        return new Error(i.name + " " + v.getMessage());
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public View findView(String fieldName) {
        for (FieldViewImpl i : fieldViewList) {
            if (i.field.getName().equals(fieldName)) {
                return i.view;
            }
        }
        return null;
    }
    public FieldViewImpl findFieldView(String fieldName) {
        for (FieldViewImpl i : fieldViewList) {
            if (i.field.getName().equals(fieldName)) {
                return i;
            }
        }
        return null;
    }

    private List<View> createViews(Object entity, boolean editable, boolean valueAlignRIght) {
        List<View> views = new ArrayList<>();
        Field[] fields = getAllFields(entity.getClass(), true);
        List<Pair<Field, ViewProps>> propertyList = new ArrayList<>();
        for (Field f : fields) {
            if (excludeFields != null && excludeFields.contains(f.getName())) {
                continue;
            }
            ViewProps property = f.getAnnotation(ViewProps.class);
            if (property == null) {
                continue;
            }
            propertyList.add(new Pair<>(f, property));
        }

        Collections.sort(propertyList, (o1, o2) -> o1.second.sort() - o2.second.sort());

        for (Pair<Field, ViewProps> pair : propertyList) {
            FieldViewImpl impl = createFieldView(entity, pair.first, pair.second, editable);
            if (impl != null) {
                impl.valueAlignRight = valueAlignRIght;
                fieldViewList.add(impl);
                View view = impl.createView(getContext());
                view.setClickable(impl.editable);
                views.add(view);
            }
        }
        return views;
    }


    private FieldViewImpl createFieldView(Object entity, Field f, ViewProps property, boolean editable) {
        try {
            DefaultValueWrapper valueWrapper = property.wrapperClazz().newInstance();
            String name = property.name();
            if (TextUtils.isEmpty(name)) {
                name = f.getName();
            }

            List<ConstraintValidator> validators = null;
            FieldViewImpl impl = null;
            Annotation[] anns = f.getAnnotations();
            for (Annotation a : anns) {
                FieldView b = a.annotationType().getAnnotation(FieldView.class);
                if (b != null) {
                    Map<String, Object> props = null;
                    if (fieldDynamicProps != null) {
                        props = fieldDynamicProps.get(f.getName());
                    }
                    impl = b.value().newInstance().create(a, props);
                }
                Constraint c = a.annotationType().getAnnotation(Constraint.class);
                if (c != null) {
                    if (validators == null) {
                        validators = new ArrayList<>();
                    }
                    for (Class clz : c.validatedBy()) {
                        ConstraintValidator vobj = (ConstraintValidator) clz.newInstance();
                        vobj.initialize(a);
                        validators.add(vobj);
                    }
                }
            }

            if (impl != null) {
                impl.valueWrapper = valueWrapper;
                impl.editable = editable && property.editable();
                impl.field = f;
                impl.entity = entity;
                impl.name = name + ": ";
                impl.validators = validators;
                return impl;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(List<String> excludeFields) {
        this.excludeFields = excludeFields;
    }

    public void setFieldDynamicProps(Map<String, Map<String, Object>> fieldDynamicProps) {
        this.fieldDynamicProps = fieldDynamicProps;
    }

    public void setFieldDynamicProps(String fieldName, Map<String, Object> fieldDynamicProps) {
        if (this.fieldDynamicProps == null) {
            this.fieldDynamicProps = new HashMap<>();
        }
        this.fieldDynamicProps.put(fieldName, fieldDynamicProps);
    }

    public Map<String, Map<String, Object>> getFieldDynamicProps() {
        return fieldDynamicProps;
    }

    //获取所有成员变量 包括父类
    public static Field[] getAllFields(Class clazz, boolean setAccess) {
        List<Field> list = new ArrayList<>();
        getAllFields(list, clazz);
        if (setAccess) {
            for (Field f : list) {
                f.setAccessible(true);
            }
        }
        return list.toArray(new Field[]{});
    }
    private static void getAllFields(List<Field> list, Class clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                list.add(field);
            }
        }
        if (clazz.getSuperclass() != Object.class) {
            getAllFields(list, clazz.getSuperclass());
        }
    }

}
