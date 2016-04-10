package ge.turtlecat.theorytest.ui.tools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import ge.turtlecat.theorytest.ui.App;

/**
 * Created by Alex on 11/21/2015.
 */
public class Settings {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;
    private static final String SHOW_ANSWERED = "SHOW_ANSWERED";
    private static final String LAST_TICKET_IDS = "LAST_TICKET_IDS";
    private static final String LAST_TICKET_INDEX = "LAST_TICKET_INDEX";
    private static final String SHOULD_REPEAT = "SHOULD_REPEAT";

    static {
        prefs = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        prefsEditor = prefs.edit();
    }


    private static void commit() {
        prefsEditor.commit();
    }


    public static void setShouldShowCorrAnswered(boolean shouldShow) {
        prefsEditor.putBoolean(SHOW_ANSWERED, shouldShow);
        commit();
    }

    public static boolean shouldShowCorrAnswered() {
        return prefs.getBoolean(SHOW_ANSWERED, false);
    }

    public static void setLastTicketIds(String ids) {
        prefsEditor.putString(LAST_TICKET_IDS, ids);
        commit();
    }

    public static String[] getLastTicketIds() {
        String lastTickets = prefs.getString(LAST_TICKET_IDS, "");

        String[] ids = null;
        if (!TextUtils.isEmpty(lastTickets))
            ids = lastTickets.split(",");

        return ids;
    }


    public static void setLastTicketIndex(int index) {
        prefsEditor.putInt(LAST_TICKET_INDEX, index);
        commit();
    }

    public static void repeatCorrectAnswers(boolean shouldRepeat) {
        prefsEditor.putBoolean(SHOULD_REPEAT, shouldRepeat);
        commit();
    }

    public static boolean repeatCorrectAnswers() {
        return prefs.getBoolean(SHOULD_REPEAT, false);
    }

    public static int getLastTicketIndex() {
        return prefs.getInt(LAST_TICKET_INDEX, 0);
    }
}
