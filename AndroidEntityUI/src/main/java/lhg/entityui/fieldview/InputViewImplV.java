package lhg.entityui.fieldview;

import android.content.Context;
import android.view.View;

import lhg.entityui.R;

public class InputViewImplV extends InputViewImpl {
    @Override
    public View onCreateView(Context context) {
        if (editable) {
            return _2input(context, R.layout.entityui_edit_item_2v, name, entity, field, valueWrapper, editable);
        } else {
            return _2input(context, R.layout.entityui_label_item_2v, name, entity, field, valueWrapper, editable);
        }
    }
}
