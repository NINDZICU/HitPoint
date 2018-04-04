package ru.kpfu.itis.alliance.heatmap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.kpfu.itis.alliance.R;

public class HeatmapActivity extends AppCompatActivity {
    private HeatmapHandler mHeatmapHandler;
    private List<TouchInfo> motionEvents = new ArrayList<>();

    private List<String> findViewsAt(ViewGroup viewGroup, int x, int y) {
        List<String> ids = new LinkedList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                ids.addAll(findViewsAt((ViewGroup) child, x, y));
            } else {
                int[] location = new int[2];
                child.getLocationOnScreen(location);
                Rect rect = new Rect(location[0], location[1], location[0] + child.getWidth(), location[1] + child.getHeight());
                if (rect.contains(x, y)) {
                    try {
                        ids.add(child.getResources().getResourceName(child.getId()));
                    } catch (Exception e) {
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("FOR TOLYA CHECK ", "PRIVET");
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            motionEvents.add(new TouchInfo(ev));
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            motionEvents.add(new TouchInfo(ev));
        }
        Log.d("MOTION EVENT ", String.valueOf(ev.getAction()));
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            motionEvents.add(new TouchInfo(ev));

            final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            List<String> ids = findViewsAt(viewGroup, (int) ev.getX(), (int) ev.getY());
            for (String id : ids) {
                System.err.println("Touch id is:" + id);
            }
        }
        mHeatmapHandler.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void takeScreenshot(List<TouchInfo> motionEvents) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
//            v1.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height)
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            Bitmap bitmap = Bitmap.createBitmap(v1.getLayoutParams().width , v1.getLayoutParams().height,
//                    Bitmap.Config.ARGB_8888);
            v1.setDrawingCacheEnabled(false);
            Canvas canvas = new Canvas(bitmap);
            Paint point = new Paint();
            Paint background = new Paint();
            point.setARGB(50, 237, 126, 23);
            point.setStrokeWidth(45);
            background.setARGB(60, 0, 0, 255);
            canvas.drawRect(0.0f, 0.0f, Float.parseFloat(Integer.toString(v1.getWidth())),
                    Float.parseFloat(Integer.toString(v1.getHeight())), background);

//            canvas.drawColor(Color.BLUE, PorterDuff.Mode.LIGHTEN);
            float start =0;
            float finish =0;
            for (TouchInfo ev : motionEvents) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    start = ev.getX();
                    finish = ev.getY();
                    canvas.drawCircle(start, finish, 50, point);

                } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                    canvas.drawLine(start, finish, ev.getX(), ev.getY(), point);
                    start = ev.getX();
                    finish = ev.getY();
                }
                else if (ev.getAction() == MotionEvent.ACTION_UP) {
                    canvas.drawLine(start, finish, ev.getX(), ev.getY(), point);
                    start = 0;
                    finish =0;
                }

            }

            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CheckPermissionService checkPermissionService = new CheckPermissionService(this);
        if (checkPermissionService.checkPermissionsStorage()) {
            takeScreenshot(this.motionEvents);
        }

    }


}