package ru.kpfu.itis.alliance.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ru.kpfu.itis.alliance.Constants;
import ru.kpfu.itis.alliance.R;

public class TypeOfSystemActivity extends AppCompatActivity {
    private RadioButton cbComposit;
    private RadioButton cbKeramogranit;
    private RadioButton cbFibroplita;
    private RadioButton cbMetalokaseta;
    private RadioGroup radioGroupTypes;
    private TextView toolbarTitle;

    private int typeCladding = 0;

    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_system);
        cbComposit = findViewById(R.id.checkBox_composite);
        cbKeramogranit = findViewById(R.id.checkBox_keramogranit);
        cbFibroplita = findViewById(R.id.checkBox_fibroplita);
        cbMetalokaseta = findViewById(R.id.checkBox_metalokaseta);
        radioGroupTypes = findViewById(R.id.radio_group_types);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        typeCladding = intent.getIntExtra("type", 0);

        toolbarTitle = myToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Тип системы");

        chooseRadioButton(typeCladding);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra("type", getStateRadioButton());
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("type", getStateRadioButton());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void chooseRadioButton(Integer typeCladding) {
        switch (typeCladding) {
            case Constants.COMPOSITE:
                cbComposit.setChecked(true);
                break;
            case Constants.KERAMOGRANITE:
                cbKeramogranit.setChecked(true);
                break;
            case Constants.FIBROPLITA:
                cbFibroplita.setChecked(true);
                break;
            case Constants.METALLOKASSETA:
                cbMetalokaseta.setChecked(true);
        }
    }

    public Integer getStateRadioButton() {
        if (cbComposit.isChecked()) return Constants.COMPOSITE;
        if (cbKeramogranit.isChecked()) return Constants.KERAMOGRANITE;
        if (cbFibroplita.isChecked()) return Constants.FIBROPLITA;
        if (cbMetalokaseta.isChecked()) return Constants.METALLOKASSETA;
        return Constants.COMPOSITE;
    }
}