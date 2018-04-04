package ru.kpfu.itis.alliance.heatmap;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.kpfu.itis.alliance.GOD.CommentaryDialogue;
import ru.kpfu.itis.alliance.R;

public class HeatmapActivity extends AppCompatActivity {
    private HeatmapHandler mHeatmapHandler;

    private List<String> findViewsAt(ViewGroup viewGroup, int x, int y){
        List<String> ids = new LinkedList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup){
                ids.addAll(findViewsAt((ViewGroup) child, x, y ));
            } else {
                int[] location = new int[2];
                child.getLocationOnScreen(location);
                Rect rect = new Rect(location[0], location[1], location[0] + child.getWidth(), location[1] + child.getHeight());
                if (rect.contains(x, y)){
                    try {
                        ids.add(child.getResources().getResourceName(child.getId()));
                    }catch (Exception e){
                        ids.add(e.getMessage() + " " + child.getId());
                    }
                }
            }
        }
        return ids;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHeatmapHandler = new HeatmapHandler(this);


        final View viewElement = getLayoutInflater().inflate(R.layout.activity_main, null, false);
        ViewParent viewParent = getWindow().getDecorView().findViewById(android.R.id.content);

        LayoutInflater layoutInflater = getLayoutInflater();

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);// вью этого класса
        List<View> views3 = new ArrayList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            views3.add(viewGroup.getChildAt(i));
        }

//        Layout layout = findViewById(R.layout.activity_main);

        List<View> views = viewElement.getTouchables();
        List<View> views1 = viewElement.getFocusables(0);
        for (View view : views) {
            System.out.println("BULAK  " + view.getId());
        }
        for (View view : views1) {
            System.out.println("BULAT1  " + view.getId());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    protected void onDestroy() {
//        mHeatmapHandler.close();
//        super.onDestroy();
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
//            final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
//            List<String> ids = findViewsAt(viewGroup, (int) ev.getX(), (int) ev.getY());
//            for (String id : ids) {
//                System.err.println("Touch id is:" + id);
//            }
            CommentaryDialogue dialogue = new CommentaryDialogue(this);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialogue.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            dialogue.show();

            dialogue.getWindow().setAttributes(lp);
        }
        mHeatmapHandler.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }


}