package ge.turtlecat.theorytest.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ge.turtlecat.theorytest.ui.tm.TicketManager;

/**
 * Created by Alex on 11/21/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected TicketManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tm = TicketManager.getInstance();
        setContentView(getLayoutId());
        onCreate();
    }

    protected abstract int getLayoutId();

    protected abstract void onCreate();
}
