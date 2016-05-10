package ge.turtlecat.theorytest.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.ui.tm.TicketLoadListener;
import ge.turtlecat.theorytest.ui.tools.Settings;
import ge.turtlecat.theorytest.ui.tools.Tools;


public class MainActivity extends BaseActivity implements TicketLoadListener {

    private Button continueButton;
    private ProgressDialog progressDialog;
    private TextView doneAmountTV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate() {
        continueButton = (Button) findViewById(R.id.continue_last_test_button);
        doneAmountTV = (TextView) findViewById(R.id.done_amount);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("მიმდინარეობს ჩატვირთვა");
        progressDialog.setCancelable(false);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doneAmountTV.setText(tm.getDoneAmount() + "/1032");
        if (Settings.getLastTicketIds() == null) {
            continueButton.setVisibility(View.GONE);
        } else {
            continueButton.setVisibility(View.VISIBLE);
        }
    }

    public void startTest(View view) {
        progressDialog.show();
        tm.loadTestTickets(this, false);
    }


    public void wrongAnswered(View view) {
        if (tm.loadWrongTickets()) {
            Intent i = new Intent(this, TicketFragmentPagerActivity.class);
            i.putExtra("wrongAnswers", true);
            i.putExtra("test", false);
            i.putExtra("lastTest", false);
            startActivity(i);
        } else {
            Tools.showToast("თქვენ არ გაქვთ შეცდომები");
        }
    }


    public void continueLastTest(View view) {
        if (tm.loadLastTestTickets()) {
            Intent i = new Intent(this, TicketFragmentPagerActivity.class);
            i.putExtra("wrongAnswers", false);
            i.putExtra("lastTest", true);
            startActivity(i);
        }
    }

    @Override
    public void onTicketsLoaded() {
        runOnUiThread(ticketsLoadedRunnable);
    }

    Runnable ticketsLoadedRunnable = new Runnable() {
        @Override
        public void run() {
            progressDialog.hide();
            Intent i = new Intent(MainActivity.this, TicketFragmentPagerActivity.class);
            i.putExtra("wrongAnswers", false);
            i.putExtra("lastTest", false);
            startActivity(i);
        }
    };

    public void wrongTest(View view) {
        if (tm.loadWrongTickets()) {
            Intent i = new Intent(this, TicketFragmentPagerActivity.class);
            i.putExtra("wrongAnswers", true);
            i.putExtra("test", true);
            i.putExtra("lastTest", false);
            startActivity(i);
        } else {
            Tools.showToast("თქვენ არ გაქვთ შეცდომები");
        }
    }

    public void startTestFull(View view) {
        progressDialog.show();
        tm.loadTestTickets(this, true);
    }
}
