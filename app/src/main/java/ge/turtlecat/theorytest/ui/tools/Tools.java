package ge.turtlecat.theorytest.ui.tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import ge.turtlecat.theorytest.ui.App;

/**
 * Created by Alex on 7/17/2015.
 */
public class Tools {

    public static void log(String tag, String msg) {
        Log.d(tag, msg);
    }


    public static void log(Object msg) {
        log("LOG", msg + "");
    }

    public static int getResId(String resName, String type) {
        Context c = getApplicationContext();
        return c.getResources().getIdentifier(resName, type, c.getPackageName());
    }

    public static int getStringResId(String resName) {
        return getResId(resName, "string");
    }

    public static Context getApplicationContext() {
        return App.getInstance();
    }

    public static int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static int getColor(int colorId) {
        Context c = getApplicationContext();
        if (getAndroidVersion() >= Build.VERSION_CODES.M) {
            return c.getResources().getColor(colorId, null);
        } else {
            return c.getResources().getColor(colorId);
        }
    }

    public static void removeGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public static void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static Bitmap getBitmapFromAssets(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("images/" + strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

}