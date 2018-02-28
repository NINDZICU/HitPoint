package ru.kpfu.itis.alliance.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.kpfu.itis.alliance.R;
import ru.kpfu.itis.alliance.activities.CalculateActivity;
import ru.kpfu.itis.alliance.models.CalculationResult;

/**
 * Created by hlopu on 08.12.2017.
 */

public class ResultFragment extends Fragment {

    private TextView tvCountMaterials;
    private TextView toolbarTitle;
    private CalculateActivity context;
    private Button btnCalculateAgain;
//    private List<CalculationResult> calculationResults;

    public static ResultFragment newInstance(List<CalculationResult> list, int typeCladdingValue) {
        Bundle args = new Bundle();
        args.putSerializable("results", (Serializable) list);
        args.putInt("typeCladding", typeCladdingValue);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;

    }

//    public void setCalculationResults(List<CalculationResult> calculationResults) {
//        this.calculationResults = calculationResults;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = (CalculateActivity) getContext(); //ToDo сделать передачу контекста нормальной
        final List<CalculationResult> calculationResults = (List<CalculationResult>) getArguments().getSerializable("results");
        final int type = getArguments().getInt("typeCladding");
        System.out.println("ALASLLASLS " + calculationResults.size());
        Toolbar myToolbar = view.findViewById(R.id.my_toolbar);
        context.setSupportActionBar(myToolbar);
        tvCountMaterials = view.findViewById(R.id.tv_count_materials);
        btnCalculateAgain = view.findViewById(R.id.btn_calculate_again);

        context.getSupportActionBar().setDisplayShowTitleEnabled(false);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context.getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle = myToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.output_result);

        tvCountMaterials.setText(tvCountMaterials.getText().toString() + type);

        final LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_elements);
        List<View> elements = new ArrayList<>();
        for (CalculationResult calculationResult : calculationResults) {
            final View viewElement = getLayoutInflater().inflate(R.layout.result_element, null, false);
            TextView nameElement = viewElement.findViewById(R.id.tv_results_name);
            TextView countElement = viewElement.findViewById(R.id.tv_results_count);
            nameElement.setText(calculationResult.getTitle());
            System.out.println("AAAAAAAAAAA " + calculationResult.getResultValue());
//            countElement.setText(R.string.count_materials + calculationResult.getResultValue());
            elements.add(viewElement);
            linear.addView(viewElement);
        }

        btnCalculateAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
