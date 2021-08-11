package lhg.entityui.fieldview;

import android.content.Context;
import android.view.View;

import lhg.entityui.R;

public class ChoiceViewImplH extends ChoiceViewImpl {
    @Override
    public View onCreateView(Context context) {
        return _2choice(context, R.layout.entityui_label_item_2h, name, entity, field, valueWrapper, values, names);
    }
}
