package ru.kpfu.itis.alliance.heatmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class HeatmapHandler {
    private static final String TAG = HeatmapHandler.class.getSimpleName();
    private Point mScreenSize;
    private String mLocalClassName;

    public HeatmapHandler(Activity activity) {
        mLocalClassName = activity.getLocalClassName();


        // get the size of the screen for tagging the data
        mScreenSize = new Point();
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getSize(mScreenSize);
    }


    public void dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            return;
        }


        Log.d(TAG, "Touch event logged: " + ev);

    }

    public Bitmap takeScreenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        return bitmap;
    }

    public Canvas paintHeatMap(Canvas canvas, MotionEvent ev, View view) {
        Paint p = new Paint();
        p.setARGB(50, 255, 0, 0);
        canvas.drawCircle(ev.getX(), ev.getY(), 50, p);
        return canvas;
//        saveImage(View.);
//        view.draw(canvas);
    }

    public void saveImage(View v1) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

//            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

}
