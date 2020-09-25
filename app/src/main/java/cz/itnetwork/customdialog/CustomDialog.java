package cz.itnetwork.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;


public class CustomDialog extends Dialog {

    public enum DialogType {INFO, WARN, ERROR, YES_NO, INPUT, SELECT}

    RelativeLayout header;
    TextView labelTitle;
    ImageView imgIcon;
    TextView labelMessage;
    EditText etInput;
    ListView listView;

    TextView btnLeft;
    TextView btnRight;

    // Barvy hlavičky dialogu
    final int colorInfo = 0xFF0098D6;     // Modrá
    final int colorWarn = 0xFFD67A00;     // Oranžová
    final int colorError = 0xFFD60000;    // Červená
    final int colorYesNo = 0xFFA21F96;    // Fialová
    final int colorInput = 0xFFA75700;    // Hnědá
    final int colorSelect = 0xFFC0BC13;    // Žlutá

    @DrawableRes
    int iconId;

    String title;
    String message;
    String[] items = new String[] {"no items!"};

    // Defaultní typ dialogu
    DialogType dialogType = DialogType.INFO;

    // Defaultní barva dialogu
    int color = colorInfo;

    OnCustomDialogButtonClickListener listener;


    public CustomDialog(Context context, DialogType type) {
        super(context);
        this.dialogType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        header = findViewById(R.id.header);
        labelTitle = findViewById(R.id.labelTitle);
        imgIcon = findViewById(R.id.imgIcon);
        labelMessage = findViewById(R.id.labelMessage);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        etInput = findViewById(R.id.etInput);
        listView = findViewById(R.id.listView);

        setDialogColor();
        labelTitle.setText(title);
        labelMessage.setText(message);

        if (iconId != 0) {
            imgIcon.setImageDrawable(AppCompatResources.getDrawable(getContext(), iconId));
        }

        if (dialogType == DialogType.INFO || dialogType == DialogType.WARN || dialogType == DialogType.ERROR) {
            btnLeft.setVisibility(View.GONE);
            btnRight.setText("OK");
            etInput.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (dialogType == DialogType.YES_NO) {
            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setText("Ne");
            btnRight.setText("Ano");
            etInput.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (dialogType == DialogType.INPUT) {
            btnLeft.setVisibility(View.GONE);
            btnRight.setText("OK");
            etInput.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else if (dialogType == DialogType.SELECT) {
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.GONE);
            etInput.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            labelMessage.setVisibility(View.GONE);

            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1
            );

            for (int i = 0; i < items.length; i ++) {
                arrayAdapter.add(items[i]);
            }

            listView.setAdapter(arrayAdapter);

            /*
            listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (listener != null) {
                        listener.onCustomDialogItemSelected(((TextView) view).getText().toString(), position);
                    }

                    dismiss();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            */

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listener != null) {
                        listener.onCustomDialogItemSelected(((TextView) view).getText().toString(), position);
                    }

                    dismiss();
                }
            });
        }

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (dialogType == DialogType.YES_NO) {
                        listener.onCustomDialogNoClicked();
                    }
                }

                dismiss();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (dialogType == DialogType.INFO || dialogType == DialogType.WARN || dialogType == DialogType.ERROR) {
                        listener.onCustomDialogOkClicked();
                    } else if (dialogType == DialogType.YES_NO) {
                        listener.onCustomDialogYesClicked();
                    } else if (dialogType == DialogType.INPUT) {
                        listener.onCustomDialogInputInserted(etInput.getText().toString());
                    }
                }

                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public void setIcon(int iconId) {
        this.iconId = iconId;
    }

    public void setListener(OnCustomDialogButtonClickListener listener) {
        this.listener = listener;
    }

    private void setDialogColor() {
        switch (dialogType) {
            case INFO:
                color = colorInfo;
                break;
            case WARN:
                color = colorWarn;
                break;
            case ERROR:
                color = colorError;
                break;
            case YES_NO:
                color = colorYesNo;
                break;
            case INPUT:
                color = colorInput;
                break;
            case SELECT:
                color = colorSelect;
                break;
        }

        header.setBackgroundColor(color);
    }

    public interface OnCustomDialogButtonClickListener {
        void onCustomDialogOkClicked();
        void onCustomDialogYesClicked();
        void onCustomDialogNoClicked();
        void onCustomDialogInputInserted(String inputText);
        void onCustomDialogItemSelected(String selectedItem, int position);
    }
}
