package lhg.entityui;

import android.text.Editable;
import android.text.TextWatcher;

import lhg.entityui.annotation.ValueWrapper;

import java.lang.reflect.Field;

public class EditTextWatcher implements TextWatcher {
    Object entity;
    Field field;
    ValueWrapper valueWrapper;

    public EditTextWatcher(Field field, Object entity, ValueWrapper valueWrapper) {
        this.entity = entity;
        this.field = field;
        this.valueWrapper = valueWrapper;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String value = s.toString();
        try {
            valueWrapper.set(field, entity, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
