package cz.itnetwork.customdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInfo;
    Button btnWarn;
    Button btnError;
    Button btnYesNo;
    Button btnInput;
    Button btnSelect;
    Button btnCustomDatePicker;
    Button btnCustomTimePicker;

    CustomDialog.OnCustomDialogButtonClickListener listener = new CustomDialog.OnCustomDialogButtonClickListener() {
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

        btnInfo = findViewById(R.id.btnInfo);
        btnWarn = findViewById(R.id.btnWarn);
        btnError = findViewById(R.id.btnError);
        btnYesNo = findViewById(R.id.btnYesNo);
        btnInput = findViewById(R.id.btnInput);
        btnSelect = findViewById(R.id.btnSelect);
        btnCustomDatePicker = findViewById(R.id.btnCustomDatePicker);
        btnCustomTimePicker = findViewById(R.id.btnCustomTimePicker);

        btnInfo.setOnClickListener(this);
        btnWarn.setOnClickListener(this);
        btnError.setOnClickListener(this);
        btnYesNo.setOnClickListener(this);
        btnInput.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnCustomDatePicker.setOnClickListener(this);
        btnCustomTimePicker.setOnClickListener(this);
    }

    private void showInfo(String text) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setTextColor(getResources().getColor(R.color.snackbar_text_color))
                .setAction("Zavřít", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
    }

    public void showCustomDialogInfo() {
        CustomDialog dialog = new CustomDialog(this, CustomDialog.DialogType.INFO);
        dialog.setTitle("Info");
        dialog.setMessage("Toto je vlastní dialog s informační zprávou.");
        dialog.setIcon(R.drawable.ic_dialog_info);
        dialog.setListener(listener);
        dialog.show();
    }

    public void showCustomDialogWarn() {
        CustomDialog dialog = new CustomDialog(this, CustomDialog.DialogType.WARN);
        dialog.setTitle("Upozornění");
        dialog.setMessage("Toto je vlastní dialog s upozorněním.");
        dialog.setIcon(R.drawable.ic_dialog_warn);
        dialog.setListener(listener);
        dialog.show();
    }

    public void showCustomDialogError() {
        CustomDialog dialog = new CustomDialog(this, CustomDialog.DialogType.ERROR);
        dialog.setTitle("Chyba");
        dialog.setMessage("Toto je vlastní chybový dialog.");
        dialog.setIcon(R.drawable.ic_dialog_error);
        dialog.setListener(listener);
        dialog.show();
    }

    public void showCustomDialogYesNo() {
        CustomDialog dialog = new CustomDialog(this, CustomDialog.DialogType.YES_NO);
        dialog.setTitle("Odhálšení");
        dialog.setMessage("Opravdu se chcete odhlásit?");
        dialog.setIcon(R.drawable.ic_dialog_yes_no);
        dialog.setListener(listener);
        dialog.show();
    }

    public void showCustomDialogInput() {
        CustomDialog dialog = new CustomDialog(this, CustomDialog.DialogType.INPUT);
        dialog.setTitle("Input");
        dialog.setMessage("Zadej nějaký text:");
        dialog.setIcon(R.drawable.ic_dialog_edit);
        dialog.setListener(listener);
        dialog.show();
    }

    public void showCustomDialogSelect() {
        CustomDialog dialog = new CustomDialog(this, CustomDialog.DialogType.SELECT);
        dialog.setTitle("Výběr");
        dialog.setMessage("Vyber položku:");
        dialog.setIcon(R.drawable.ic_dialog_select);

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

        dialog.setItems(items);
        dialog.setListener(listener);
        dialog.show();
    }

    /*
    Zobrazení vlastního dialogu s nastavením data.
    */
    public void showCustomDatePicker() {
        CustomDatePicker datePicker = new CustomDatePicker(this, new Date());

        datePicker.setListener(new CustomDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("d.MMMM y");
                String dateToString = sdf.format(date);
                showInfo(dateToString);
            }
        });

        datePicker.show();
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
                showCustomDialogInfo();
                break;
            case R.id.btnWarn:
                showCustomDialogWarn();
                break;
            case R.id.btnError:
                showCustomDialogError();
                break;
            case R.id.btnYesNo:
                showCustomDialogYesNo();
                break;
            case R.id.btnInput:
                showCustomDialogInput();
                break;
            case R.id.btnSelect:
                showCustomDialogSelect();
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