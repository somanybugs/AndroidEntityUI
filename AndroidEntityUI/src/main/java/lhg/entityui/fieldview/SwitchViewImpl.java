package lhg.entityui.fieldview;

import android.content.Context;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import lhg.entityui.R;

import java.lang.reflect.Field;

public class SwitchViewImpl extends FieldViewImpl {

    private static View _switch(Context context, String title, Object entity, Field field) {
        View view = View.inflate(context, R.layout.entityui_list_item_switch, null);
        ((TextView) view.findViewById(android.R.id.text1)).setText(title);
        Switch switch1 = view.findViewById(R.id.switch1);
        try {
            switch1.setChecked(field.getBoolean(entity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                field.setBoolean(entity, isChecked);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    @Override
    public View onCreateView(Context context) {
        View view = _switch(context, name, entity, field);
        View switch1 = view.findViewById(R.id.switch1);
        if (switch1 != null) {
            switch1.setEnabled(editable);
        }
        return view;
    }
}
