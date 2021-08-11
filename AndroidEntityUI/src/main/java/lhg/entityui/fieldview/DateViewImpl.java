package lhg.entityui.fieldview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lhg.entityui.R;

public class DateViewImpl extends FieldViewImpl {
    public boolean bdate, btime;
    public String inFormat;


    Date getFieldDate() {
        Date date = null;
        try {
            date = (Date) field.get(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date == null) {
            try {
                date = new SimpleDateFormat(inFormat).parse(String.valueOf(field.get(entity)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    void setFieldDate(Date date) {
        TextView text2 = view.findViewById(android.R.id.text2);
        text2.setText(String.valueOf(DateFormat.format(inFormat, date)));
        try {
            if (Date.class.isAssignableFrom(field.getType())) {
                field.set(entity, date);
            } else {
                field.set(entity, text2.getText().toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(Context context) {
        Date date = getFieldDate();
        String text = null;
        if (date != null) {
            text = String.valueOf(DateFormat.format(inFormat, date));
        }

        View view = View.inflate(context, R.layout.entityui_label_item_2h, null);
        TextView text1 = view.findViewById(android.R.id.text1);
        TextView text2 = view.findViewById(android.R.id.text2);
        if (valueAlignRight) {
            text2.setGravity(Gravity.RIGHT);
        }
        text1.setText(name);
        text2.setText(text);
        view.setOnClickListener(v -> {
            Date date1 = getFieldDate();
            Calendar calendar = Calendar.getInstance();
            if (date1 != null) {
                calendar.setTime(date1);
            }
            if (bdate) {
                showDateDialog(calendar);
            } else if (btime) {
                showTimeDialog(calendar);
            }
        });
        return view;
    }

    void showDateDialog(Calendar calendar) {
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (btime) {
                showTimeDialog(calendar);
            } else {
                setFieldDate(calendar.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    void showTimeDialog(Calendar calendar) {
        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            setFieldDate(calendar.getTime());
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }
}