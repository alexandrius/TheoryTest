package ge.turtlecat.theorytest.ui.activities;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.bean.Ticket;
import ge.turtlecat.theorytest.ui.App;
import ge.turtlecat.theorytest.ui.components.TicketPager;
import ge.turtlecat.theorytest.ui.tools.Settings;

/**
 * Created by Alex on 11/21/2015.
 */
public class TicketFragmentPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private TicketPager ticketPager;
    private boolean wrongAnswers;
    private int corrAnswerCount, wrongAnswerCount;
    private TextView currentPageTV, corrAnswerCountTV, wrongAnswerCountTV;


    public boolean isWrongAnswers() {
        return wrongAnswers;
    }


    public int getCorrAnswerCount() {
        return corrAnswerCount;
    }

    public void setCorrAnswerCount(int corrAnswerCount) {
        this.corrAnswerCount = corrAnswerCount;
        corrAnswerCountTV.setText(corrAnswerCount + "");
    }

    public int getWrongAnswerCount() {
        return wrongAnswerCount;
    }

    public void setWrongAnswerCount(int wrongAnswerCount) {
        this.wrongAnswerCount = wrongAnswerCount;
        wrongAnswerCountTV.setText(wrongAnswerCount + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_pager;
    }

    @Override
    protected void onCreate() {

        wrongAnswers = getIntent().getBooleanExtra("wrongAnswers", false);
        corrAnswerCountTV = (TextView) findViewById(R.id.corr_answer_count);
        wrongAnswerCountTV = (TextView) findViewById(R.id.wrong_answer_count);
        ticketPager = (TicketPager) findViewById(R.id.ticket_view);
        currentPageTV = (TextView) findViewById(R.id.current_page);
        currentPageTV.setText("1/" + App.getInstance().getCurrentTickets().size());
        ticketPager.addOnPageChangeListener(this);
        ticketPager.setFM(getSupportFragmentManager());
        ticketPager.setCurrentItem(Settings.getLastTicketIndex());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int displayPos = position + 1;
        currentPageTV.setText(displayPos + "/" + App.getInstance().getCurrentTickets().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isWrongAnswers()) {

            if (wrongAnswerCount + corrAnswerCount < 30) {

                String lastTickets = "";
                for (Ticket ticket : App.getInstance().getCurrentTickets()) {
                    lastTickets = lastTickets + ticket.getId() + ",";
                }

                Settings.setLastTicketIds(lastTickets);
                Settings.setLastTicketIndex(ticketPager.getCurrentItem());
            } else {
                Settings.setLastTicketIds(null);
            }
        }
    }
}
