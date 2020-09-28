package cz.itnetwork.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CustomTimePicker extends Dialog {

    TimePicker timePicker;
    Button btnOk;

    Calendar calendar;
    Date startTime;
    int startHours;
    int startMinutes;

    OnTimeSelectedListener listener;

    public CustomTimePicker(Context context, Date startTime) {
        super(context);
        setStartTime(startTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_time_picker);

        timePicker = findViewById(R.id.timePicker);
        btnOk = findViewById(R.id.btnOk);

        timePicker.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(startHours);
            timePicker.setMinute(startMinutes);
        } else {
            timePicker.setCurrentHour(startHours);
            timePicker.setCurrentMinute(startMinutes);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = new GregorianCalendar();
                calendar.setTime(new Date());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                }

                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                if (listener != null) {
                    listener.onTimeSelected(calendar.getTime());
                }

                dismiss();
            }
        });
    }

    private void setStartTime(Date date) {
        calendar = new GregorianCalendar();
        calendar.setTime(date);

        startTime = date;
        startHours = calendar.get(Calendar.HOUR_OF_DAY);
        startMinutes = calendar.get(Calendar.MINUTE);
    }

    public void setListener(OnTimeSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(Date time);
    }
}
