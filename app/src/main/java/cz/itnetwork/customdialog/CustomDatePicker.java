package cz.itnetwork.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomDatePicker extends Dialog {

    DatePicker datePicker;
    Button btnOk;

    Calendar calendar;
    Date startDate;
    int startDay;
    int startMonth;
    int startYear;

    long minDate;
    long maxDate;

    OnDateSelectedListener listener;

    public CustomDatePicker(Context context, Date startDate) {
        super(context);
        setStartDate(startDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_date_picker);

        datePicker = findViewById(R.id.datePicker);
        btnOk = findViewById(R.id.btnOk);

        datePicker.updateDate(startYear, startMonth, startDay);

        if (minDate > 0) {
            datePicker.setMinDate(minDate);
        }

        if (maxDate > 0) {
            datePicker.setMaxDate(maxDate);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = new GregorianCalendar();
                calendar.setTime(new Date());

                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.YEAR, datePicker.getYear());
                
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                listener.onDateSelected(calendar.getTime());

                dismiss();
            }
        });
    }

    private void setStartDate(Date date) {
        if (date == null) return;

        this.startDate = date;
        this.calendar = new GregorianCalendar();
        this.calendar.setTime(this.startDate);

        this.startDay = this.calendar.get(Calendar.DAY_OF_MONTH);
        this.startMonth = this.calendar.get(Calendar.MONTH);
        this.startYear = this.calendar.get(Calendar.YEAR);
    }

    public void setMinDate(long minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(long maxDate) {
        this.maxDate = maxDate;
    }

    public void setListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }
}
