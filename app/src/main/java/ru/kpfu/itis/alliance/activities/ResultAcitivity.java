package ru.kpfu.itis.alliance.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.itis.alliance.R;
import ru.kpfu.itis.alliance.models.CalculationResult;

public class ResultAcitivity extends AppCompatActivity {

    private TextView tvCountMaterials;
    private TextView toolbarTitle;
    private Button btnCalculateAgain;
    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result);

        Intent intent = getIntent();
        final List<CalculationResult> calculationResults = (List<CalculationResult>) intent.getSerializableExtra("results");
        final String type = intent.getStringExtra("typeCladding");
        System.out.println("ALASLLASLS " + calculationResults.size());
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        tvCountMaterials = findViewById(R.id.tv_count_materials);
        btnCalculateAgain = findViewById(R.id.btn_calculate_again);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle = myToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.output_result);

        tvCountMaterials.setText(tvCountMaterials.getText().toString() +" "+ type);

        final LinearLayout linear = (LinearLayout) findViewById(R.id.ll_elements);
        List<View> elements = new ArrayList<>();
        for (CalculationResult calculationResult : calculationResults) {
            final View viewElement = getLayoutInflater().inflate(R.layout.result_element, null, false);
            TextView nameElement = viewElement.findViewById(R.id.tv_results_name);
            nameElement.setText(calculationResult.getTitle());
            TextView countElement = viewElement.findViewById(R.id.tv_results_count);
            System.out.println("AAAAAAAAAAA " + calculationResult.getResultValue());
            countElement.setText("Количество материалов: "+ calculationResult.getResultValue());
            elements.add(viewElement);
            linear.addView(viewElement);
        }

        btnCalculateAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                Intent intent = new Intent(ResultAcitivity.this, CalculateActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(RESULT_OK);
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
