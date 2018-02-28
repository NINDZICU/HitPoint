package ru.kpfu.itis.alliance.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ru.kpfu.itis.alliance.Constants;
import ru.kpfu.itis.alliance.R;

public class WhoAreActivity extends AppCompatActivity {
    private RadioButton rbDesigner;
    private RadioButton rbExecutor;
    private RadioButton rbCustomer;
    private RadioButton rbPrivatePerson;
    private RadioGroup radioGroupWhoAre;
    private TextView toolbarTitle;

    private int whoAre = CalculateActivity.CUSTOMER;

    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_are);
        rbDesigner = findViewById(R.id.checkBox_designer);
        rbExecutor = findViewById(R.id.checkBox_executor);
        rbCustomer = findViewById(R.id.checkBox_customer);
        rbPrivatePerson = findViewById(R.id.checkBox_private_person);
        radioGroupWhoAre = findViewById(R.id.radio_group_who_are);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        whoAre = intent.getIntExtra("who_are", CalculateActivity.CUSTOMER);

        toolbarTitle = myToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.who_are_you);

        chooseRadioButton(whoAre);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, CalculateActivity.class);
        intent.putExtra("who_are", getStateRadioButton());
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CalculateActivity.class);
        intent.putExtra("who_are", getStateRadioButton());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void chooseRadioButton(int whoAreYou) {
        switch (whoAreYou) {
            case CalculateActivity.CUSTOMER:
                rbCustomer.setChecked(true);
                break;
            case CalculateActivity.EXECUTOR:
                rbExecutor.setChecked(true);
                break;
            case CalculateActivity.DESIGNER:
                rbDesigner.setChecked(true);
                break;
            case CalculateActivity.PRIVATEPERSON:
                rbPrivatePerson.setChecked(true);
                break;
        }
    }

    public int getStateRadioButton() {
        if (rbDesigner.isChecked()) return CalculateActivity.DESIGNER;
        if (rbExecutor.isChecked()) return CalculateActivity.EXECUTOR;
        if (rbCustomer.isChecked()) return CalculateActivity.CUSTOMER;
        if (rbPrivatePerson.isChecked()) return CalculateActivity.PRIVATEPERSON;

        return CalculateActivity.CUSTOMER;
    }
}
