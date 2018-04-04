package ru.kpfu.itis.alliance.heatmap;

import android.view.MotionEvent;

/**
 * Created by hlopu on 28.03.2018.
 */

public class TouchInfo{
    private float x;
    private float y;
    private int action;

    public TouchInfo(MotionEvent event) {
        this.x = event.getX();
        this.y = event.getY();
        this.action = event.getAction();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getAction() {
        return action;
    }
}
