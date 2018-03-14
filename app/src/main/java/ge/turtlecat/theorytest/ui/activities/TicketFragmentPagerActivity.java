package ge.turtlecat.theorytest.ui.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.bean.Ticket;
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
    private boolean wrongTest;
    private int corrAnswerCount, wrongAnswerCount;
    private TextView currentPageTV, corrAnswerCountTV, wrongAnswerCountTV;
    private Button descriptionButton;
    private FrameLayout description_layout;
    private Button deleteFromMistakesButton;
    private DescriptionFragmentStatus status;

    public boolean isWrongAnswers() {
        return wrongAnswers;
    }

    public boolean isWrongTest() {
        return wrongTest;
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
        Toolbar toolbar =  findViewById(R.id.toolbar);
        //AppCompatActivity activity = (AppCompatActivity) getActivity();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        deleteFromMistakesButton = findViewById(R.id.delete_from_mistakes);


        descriptionButton = findViewById(R.id.description_button);
        description_layout = findViewById(R.id.description_layout);
        wrongAnswers = getIntent().getBooleanExtra("wrongAnswers", false);
        wrongTest = getIntent().getBooleanExtra("test", false);
        corrAnswerCountTV = findViewById(R.id.corr_answer_count);
        wrongAnswerCountTV = findViewById(R.id.wrong_answer_count);
        ticketPager = findViewById(R.id.ticket_view);
        try {
            ticketPager.setPageTransformer(true, new TransformerItem(AccordionTransformer.class).clazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        currentPageTV = findViewById(R.id.current_page);
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

        if (isWrongTest()) {
            deleteFromMistakesButton.setVisibility(View.VISIBLE);
            deleteFromMistakesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketPager.getCurrentFragment().deleteFromMistakes();
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isWrongAnswers()) {
            wrongAnswerCount = Settings.getLastTicketWrongAmount();
            corrAnswerCount = Settings.getLastTicketCorrectAmount();
            setCorrAnswerCount(corrAnswerCount);
            setWrongAnswerCount(wrongAnswerCount);
        }
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
                StringBuilder lastTickets = new StringBuilder();
                for (Ticket ticket : TicketManager.getInstance().getCurrentTickets()) {
                    lastTickets.append(ticket.getId()).append(",");
                }
                Settings.setLastTicketIds(lastTickets.toString());
                Settings.setLastTicketIndex(ticketPager.getCurrentItem());
                Settings.saveLastTicketCorrectAmount(corrAnswerCount);
                Settings.saveLastTicketWrongAmount(wrongAnswerCount);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                //  overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
