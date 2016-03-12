package ge.turtlecat.theorytest.ui.tm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Alex on 3/12/2016.
 */
public class DBTools {

    private static final String DB_NAME = "theory.db";

    public static boolean copyDB(Context context) {
        try {
            File dbFile = context.getDatabasePath(DB_NAME);
            if (!dbFile.exists()) {
                SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
                db.close();
                InputStream dbInput = context.getApplicationContext().getAssets().open(DB_NAME);
                String outFileName = dbFile.getAbsolutePath();
                OutputStream dbOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = dbInput.read(buffer)) > 0) {
                    dbOutput.write(buffer, 0, length);
                }
                dbOutput.flush();
                dbOutput.close();
                dbInput.close();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
