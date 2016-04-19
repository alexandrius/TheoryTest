package ge.turtlecat.theorytest.ui.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.bean.Ticket;
import ge.turtlecat.theorytest.ui.App;
import ge.turtlecat.theorytest.ui.components.TicketPager;
import ge.turtlecat.theorytest.ui.fragments.TiketDescriptionFragment;
import ge.turtlecat.theorytest.ui.tm.TicketManager;
import ge.turtlecat.theorytest.ui.tools.DescriptionFragmentStatus;
import ge.turtlecat.theorytest.ui.tools.Settings;

/**
 * Created by Alex on 11/21/2015.
 */
public class TicketFragmentPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private TicketPager ticketPager;
    private boolean wrongAnswers;
    private int corrAnswerCount, wrongAnswerCount;
    private TextView currentPageTV, corrAnswerCountTV, wrongAnswerCountTV;
    private Button descriptionButton;
    private FrameLayout description_layout;
    private DescriptionFragmentStatus status;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //AppCompatActivity activity = (AppCompatActivity) getActivity();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        descriptionButton = (Button) findViewById(R.id.description_button);
        description_layout = (FrameLayout) findViewById(R.id.description_layout);
        wrongAnswers = getIntent().getBooleanExtra("wrongAnswers", false);
        corrAnswerCountTV = (TextView) findViewById(R.id.corr_answer_count);
        wrongAnswerCountTV = (TextView) findViewById(R.id.wrong_answer_count);
        ticketPager = (TicketPager) findViewById(R.id.ticket_view);
        try {
            ticketPager.setPageTransformer(true, new TransformerItem(AccordionTransformer.class).clazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        currentPageTV = (TextView) findViewById(R.id.current_page);
        currentPageTV.setText("1/" + TicketManager.getInstance().getCurrentTickets().size());
        ticketPager.addOnPageChangeListener(this);
        ticketPager.setFM(getSupportFragmentManager());
        if (getIntent().getBooleanExtra("lastTest", false))
            ticketPager.setCurrentItem(Settings.getLastTicketIndex());

        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  description_layout.setVisibility(View.GONE);
                //  Log.d("status first", " " + status);
                if (status.isStatus()) {
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.description_layout, new TiketDescriptionFragment())
                            .commit();
                    descriptionButton.setText("წაშლა");
                    status.setStatus(false);
                } else {
                    description_layout.removeAllViews();
                    //description_layout.setVisibility(View.INVISIBLE);
                    descriptionButton.setText("განმარტება");
                    status.setStatus(true);

                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int displayPos = position + 1;
        currentPageTV.setText(displayPos + "/" + TicketManager.getInstance().getCurrentTickets().size());
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
                for (Ticket ticket : TicketManager.getInstance().getCurrentTickets()) {
                    lastTickets = lastTickets + ticket.getId() + ",";
                }
                Settings.setLastTicketIds(lastTickets);
                Settings.setLastTicketIndex(ticketPager.getCurrentItem());
            } else {
                Settings.setLastTicketIds(null);
            }
        }
    }

    private static final class TransformerItem {

        final String title;
        final Class<? extends ViewPager.PageTransformer> clazz;

        public TransformerItem(Class<? extends ViewPager.PageTransformer> clazz) {
            this.clazz = clazz;
            title = clazz.getSimpleName();
        }

        @Override
        public String toString() {
            return title;
        }

    }
}
