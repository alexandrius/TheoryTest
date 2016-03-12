package ge.turtlecat.theorytest.ui.activities;

import android.widget.ImageView;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.ui.tools.Tools;

/**
 * Created by Alex on 1/22/2016.
 */
public class FullScreenImageActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_full_screen;
    }

    @Override
    protected void onCreate() {
        ImageView imageView = (ImageView) findViewById(R.id.full_screen_image);
        String img = getIntent().getStringExtra("img");
        imageView.setImageBitmap(Tools.getBitmapFromAssets(this, img));
    }
}
