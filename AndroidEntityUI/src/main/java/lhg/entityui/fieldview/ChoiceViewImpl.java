package lhg.entityui.fieldview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import lhg.entityui.annotation.ValueWrapper;

import java.lang.reflect.Field;

public abstract class ChoiceViewImpl extends FieldViewImpl {
    public String[] values, names;
    public String hint;


    protected View _2choice(Context context, int layout, String title, Object entity, Field field, ValueWrapper valueWrapper, String[] values, String[] names) {
        View view = View.inflate(context, layout, null);
        TextView text1 = view.findViewById(android.R.id.text1);
        TextView text2 = view.findViewById(android.R.id.text2);
        if (valueAlignRight) {
            text2.setGravity(Gravity.RIGHT);
        }
        text1.setText(title);
        text2.setHint(hint);
        String[] finalNames = (names == null || names.length == 0 ? values : names);
        String row = null;
        try {
            row = valueWrapper.get(field, entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(row)) {
                text2.setText(finalNames[i]);
                break;
            }
        }
        view.setOnClickListener(v -> {
            new AlertDialog.Builder(context).setItems(finalNames, (dialog, which) -> {
                try {
                    valueWrapper.set(field, entity, values[which]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                text2.setText(finalNames[which]);
            }).show();
        });
        return view;
    }
}
