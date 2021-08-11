package lhg.entityui.fieldview;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import lhg.entityui.EditTextWatcher;
import lhg.entityui.DefaultValueWrapper;
import lhg.entityui.annotation.InputView;
import lhg.entityui.annotation.ValueWrapper;

import java.lang.reflect.Field;

public abstract class InputViewImpl extends FieldViewImpl {

    public String hint = null;
    public int enterKey = InputView.Next;
    protected TextView text1, text2;
    public int inputType = -1;

    @Override
    public void showInvalidError(String error) {
        text2.setError(error);
    }

    private View _2label(Context context, int layout, String title, Object entity, Field field, DefaultValueWrapper valueWrapper) {
        View view = View.inflate(context, layout, null);
        TextView text1 = view.findViewById(android.R.id.text1);
        TextView text2 = view.findViewById(android.R.id.text2);
        if (valueAlignRight) {
            text2.setGravity(Gravity.RIGHT);
        }
        text1.setText(title);
        try {
            text2.setText(valueWrapper.get(field, entity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return view;
    }

    protected View _2input(Context context, int layout, String title, Object entity, Field field, ValueWrapper valueWrapper, boolean editable) {
        View view = View.inflate(context, layout, null);
        text1 = view.findViewById(android.R.id.text1);
        text2 = view.findViewById(android.R.id.text2);
        if (valueAlignRight) {
            text2.setGravity(Gravity.RIGHT);
        }

        if (editable) {
            text2.setHint(hint);
        }
        text1.setText(title);

        if (enterKey == InputView.Next) {
            text2.setSingleLine();
            text2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        }

        try {
            text2.setText(valueWrapper.get(field, entity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Class fieldType = field.getType();
        if (inputType != -1) {
            text2.setInputType(inputType);
        } else {
            if (FLOAT_CLASS_LIST.contains(fieldType)) {
                text2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            } else if (INT_CLASS_LIST.contains(fieldType)) {
                text2.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }

        text2.addTextChangedListener(new EditTextWatcher(field, entity, valueWrapper));

        if (!editable) {
            text2.setFocusable(false);
            text2.setEnabled(editable);
            text2.setTextColor(Color.DKGRAY);
            return view;
        }
        view.setClickable(false);
        text2.setTextColor(Color.BLACK);
//        view.setOnClickListener(v -> {
//            text2.requestFocus();
//            if (text2 instanceof EditText) {
//                ((EditText)text2).setSelection(text2.length());
//                Utils.showKeyboard(text2);
//            }
//        });

        return view;
    }


}
