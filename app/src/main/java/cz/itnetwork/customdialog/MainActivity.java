package cz.itnetwork.customdialog;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final CustomDialog.OnCustomDialogListener listener = new CustomDialog.OnCustomDialogListener() {
        @Override
        public void onCustomDialogOkClicked() {
            showInfo("Stisknuto tlačítko OK");
        }

        @Override
        public void onCustomDialogYesClicked() {
            showInfo("Stisknuto tlačítko ANO");
        }

        @Override
        public void onCustomDialogNoClicked() {
            showInfo("Stisknuto tlačítko NE");
        }

        @Override
        public void onCustomDialogInputInserted(String inputText) {
            showInfo("Zadaný text: " + inputText);
        }

        @Override
        public void onCustomDialogItemSelected(String selectedItem, int position) {
            showInfo("Zvoleno: " + selectedItem + "\nPozice v poli: " + position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnInfo)).setOnClickListener(this);
        (findViewById(R.id.btnWarn)).setOnClickListener(this);
        (findViewById(R.id.btnError)).setOnClickListener(this);
        (findViewById(R.id.btnYesNo)).setOnClickListener(this);
        (findViewById(R.id.btnInput)).setOnClickListener(this);
        (findViewById(R.id.btnSelect)).setOnClickListener(this);
        (findViewById(R.id.btnCustomDatePicker)).setOnClickListener(this);
        (findViewById(R.id.btnCustomTimePicker)).setOnClickListener(this);
    }

    private void showInfo(String text) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE)
                .setTextColor(getResources().getColor(R.color.snackbar_text_color))
                .setAction("Zavřít", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
    }

    public void showDialog(
            CustomDialog.DialogType type,
            String title,
            String message,
            @DrawableRes int icon,
            CustomDialog.OnCustomDialogListener listener,
            String[] items) {

        CustomDialog dialog = new CustomDialog(this, type);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIcon(icon);
        dialog.setListener(listener);
        dialog.setItems(items);
        dialog.show();
    }

    /*
    Zobrazení vlastního dialogu s nastavením data.
    */
    public void showCustomDatePicker() {
        CustomDatePicker dialog = new CustomDatePicker(this, new Date());

        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 15);
        c.set(Calendar.MONTH, 7);
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        dialog.setMinDate(c.getTimeInMillis());

        dialog.setListener(new CustomDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("d.MMMM y");
                String dateToString = sdf.format(date);
                showInfo(dateToString);
            }
        });

        dialog.show();
    }

    /*
    Zobrazení vlastního dialogu s nastavením času.
    */
    public void showCustomTimePicker() {
        CustomTimePicker timePicker = new CustomTimePicker(this, new Date());

        timePicker.setListener(new CustomTimePicker.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(Date time) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String dateToString = sdf.format(time);
                showInfo(dateToString);
            }
        });

        timePicker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInfo:
                showDialog(CustomDialog.DialogType.INFO, "Info", "Toto je vlastní dialog s informační zprávou.", R.drawable.ic_dialog_info, listener, null);
                break;
            case R.id.btnWarn:
                showDialog(CustomDialog.DialogType.WARN, "Upozornění", "Toto je vlastní dialog s upozorněním.", R.drawable.ic_dialog_warn, listener, null);
                break;
            case R.id.btnError:
                showDialog(CustomDialog.DialogType.ERROR, "Chyba", "Toto je vlastní chybový dialog.", R.drawable.ic_dialog_error, listener, null);
                break;
            case R.id.btnYesNo:
                showDialog(CustomDialog.DialogType.YES_NO, "Odhlášení", "Opravdu se chcete odhlásit?", R.drawable.ic_dialog_yes_no, listener, null);
                break;
            case R.id.btnInput:
                showDialog(CustomDialog.DialogType.INPUT, "Input", "Zadej nějaký text:", R.drawable.ic_dialog_edit, listener, null);
                break;
            case R.id.btnSelect:
                String[] items = new String[] {
                        "První položka",
                        "Druhá položka",
                        "Třetí položka",
                        "Čtvrtá položka",
                        "Pátá položka",
                        "Šestá položka",
                        "Sedmá položka",
                        "Osmá položka",
                        "Devátá položka",
                        "Desátá položka",
                        "Jedenáctá položka",
                        "Dvanáctá položka",
                        "Třináctá položka",
                        "Čtrnáctá položka",
                        "Patnáctá položka"};

                showDialog(CustomDialog.DialogType.SELECT, "Výběr", "Vyber položku:", R.drawable.ic_dialog_select, listener, items);
                break;
            case R.id.btnCustomDatePicker:
                showCustomDatePicker();
                break;
            case R.id.btnCustomTimePicker:
                showCustomTimePicker();
                break;
        }
    }
}