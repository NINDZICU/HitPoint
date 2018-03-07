package ru.kpfu.itis.alliance.heatmap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class HeatmapActivity extends AppCompatActivity {
    private HeatmapHandler mHeatmapHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeatmapHandler = new HeatmapHandler(this);
    }

    @Override
    protected void onDestroy() {
        mHeatmapHandler.close();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mHeatmapHandler.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}