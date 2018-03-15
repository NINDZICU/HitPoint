package ru.kpfu.itis.alliance.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.itis.alliance.R;
import ru.kpfu.itis.alliance.heatmap.HeatmapActivity;

public class MainActivity extends HeatmapActivity {

    Activity context = this;
    Button toCalculate;
    Button toCompanyInfo;

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


}
