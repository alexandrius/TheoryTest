package ge.turtlecat.theorytest.ui;

import android.app.Application;
import com.orm.SugarContext;
import ge.turtlecat.theorytest.ui.tm.DBTools;

/**
 * Created by Alex on 11/21/2015.
 */
public class App extends Application {

    private static App appInstance;


    public static App getInstance() {
        return appInstance;
    }


    @Override
    public final void onCreate() {
        DBTools.copyDB(this);
        super.onCreate();
        SugarContext.init(this, false);
        appInstance = this;
    }
}
