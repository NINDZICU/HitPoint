package ru.kpfu.itis.alliance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ru.kpfu.itis.alliance.GOD.GodClass;
import ru.kpfu.itis.alliance.R;

public class MainActivity extends AppCompatActivity {

    Activity context = this;
    Button toCalculate;
    GodClass god = new GodClass();
    Button toCompanyInfo;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toCalculate = findViewById(R.id.calculate);
        toCompanyInfo = findViewById(R.id.company_info);


        toCompanyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CompanyInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        toCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CalculateFragment calculateFragment = CalculateFragment.newInstance();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_calculate, calculateFragment, calculateFragment.getTag()).commit();
                Intent intent = new Intent(context, CalculateActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (god.isState()) {


            god.state = !god.state;
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                flag++;
                Toast.makeText(this, Integer.toString(flag), Toast.LENGTH_SHORT).show();
                return super.dispatchTouchEvent(ev);

            }
        }
        return super.dispatchTouchEvent(ev);
    }
}