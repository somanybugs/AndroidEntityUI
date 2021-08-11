package lhg.entityui.fieldview;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import lhg.entityui.R;

import java.util.HashSet;
import java.util.Set;

public class InputViewImplHAuto extends InputViewImpl {
    AutoCompleteTextView autoCompleteTextView;

    @Override
    public View onCreateView(Context context) {
        View view = _2input(context, R.layout.entityui_edit_item_2h_auto, name, entity, field, valueWrapper, editable);
        autoCompleteTextView = (AutoCompleteTextView) text2;
        initAutoComplete(autoCompleteTextView);
        return view;
    }

    String key() {
        return entity.getClass().getSimpleName() + "-" + field.getName();
    }

    public void saveHistory() {
        SharedPreferences sp = view.getContext().getSharedPreferences("entity_auto_list", Context.MODE_PRIVATE);
        Set<String> set = sp.getStringSet(key(), new HashSet<>());
        String text = autoCompleteTextView.getText().toString();
        if (TextUtils.isEmpty(text) || set.contains(text)) {
            return;
        }
        Set<String> set2 = new HashSet<>(set);
        set2.add(text);
        sp.edit().putStringSet(key(), set2).commit();
    }

    private void initAutoComplete(AutoCompleteTextView autoCompleteTextView) {
        Context context = autoCompleteTextView.getContext();
        SharedPreferences sp = context.getSharedPreferences("entity_auto_list", Context.MODE_PRIVATE);
        Set<String> set = sp.getStringSet(key(), null);
        if (set == null || set.isEmpty()) {
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(context,
                android.R.layout.simple_dropdown_item_1line, set.toArray(new String[0]));
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            AutoCompleteTextView view = (AutoCompleteTextView) v;
            if (hasFocus) {
                view.showDropDown();
            }
        });
    }
}
