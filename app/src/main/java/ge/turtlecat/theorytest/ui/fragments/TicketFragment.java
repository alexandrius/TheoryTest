package ge.turtlecat.theorytest.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.bean.Ticket;
import ge.turtlecat.theorytest.ui.tools.Tools;
import ge.turtlecat.theorytest.ui.activities.FullScreenImageActivity;
import ge.turtlecat.theorytest.ui.activities.TicketFragmentPagerActivity;

/**
 * Created by Alex on 11/21/2015.
 */
public class TicketFragment extends BaseFragment implements View.OnClickListener {

    private TextView questionTV;
    private Ticket currentTicket;
    private LinearLayout ticketLayout;
    private Button[] buttons;
    private ImageView ticketImage;
    //TODO: კოდი ბინძურია, ესაჭიროება დასუფთავება

    private boolean wrongAnswers;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ticket;
    }

    @Override
    protected void onCreate() {
        // descriptionButton=(Button)findViewById(R.id.description_button);
        ticketLayout = (LinearLayout) findViewById(R.id.ticket_layout);
        currentTicket = tm.getCurrentTickets().get(getArguments().getInt("ticket"));
        ticketImage = (ImageView) findViewById(R.id.ticket_question_image);
        questionTV = (TextView) findViewById(R.id.ticket_question_text);
        questionTV.setText(currentTicket.getQuestion());
        int answerCnt = currentTicket.getAnswerArray().length;
        buttons = new Button[answerCnt];
        for (int i = 0; i < answerCnt; i++) {
            Button button = new Button(getContext());
            button.setTag(i + 1);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ticketLayout.addView(button);
            button.setLayoutParams(buttonParams);
            button.setOnClickListener(this);
            button.setTextColor(Color.BLACK);
            button.setText(currentTicket.getAnswerArray()[i]);
            buttons[i] = button;
        }
        ////description
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Description", currentTicket.getDescription());
        editor.apply();
/////
        if (!TextUtils.isEmpty(currentTicket.getImg())) {
            Bitmap b = Tools.getBitmapFromAssets(getContext(), currentTicket.getImg());

            if (b != null) {
                ticketImage.setImageBitmap(b);
                ticketImage.setOnClickListener(this);
            } else {
                ticketImage.setVisibility(View.GONE);
            }
        } else {
            ticketImage.setVisibility(View.GONE);
        }
    }

    private void markButtons(int clicked) {
        buttons[clicked - 1].setBackgroundColor(clicked == currentTicket.getCorrAnswer() ? Color.GREEN : Color.RED);
        buttons[currentTicket.getCorrAnswer() - 1].setBackgroundColor(Color.GREEN);
        for (Button b : buttons) {
            b.setEnabled(false);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wrongAnswers = ((TicketFragmentPagerActivity) getActivity()).isWrongAnswers();
        if (currentTicket.getAnswered() != -1) {
            markButtons(currentTicket.getAnswered());
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            int answer = (int) v.getTag();

            if (!wrongAnswers) {
                currentTicket.setAnswered(answer);
                currentTicket.save();
            }

            TicketFragmentPagerActivity activity = (TicketFragmentPagerActivity) getActivity();
            if (answer == currentTicket.getCorrAnswer()) {
                v.setBackgroundColor(Color.GREEN);
                activity.setCorrAnswerCount(activity.getCorrAnswerCount() + 1);


            } else {
                buttons[currentTicket.getCorrAnswer() - 1].setBackgroundColor(Color.GREEN);
                v.setBackgroundColor(Color.RED);

                activity.setWrongAnswerCount(activity.getWrongAnswerCount() + 1);
            }

            for (Button b : buttons) {
                b.setEnabled(false);
            }
        } else {
            Intent i = new Intent(getContext(), FullScreenImageActivity.class);
            i.putExtra("img", currentTicket.getImg());
            startActivity(i);
        }
    }


}
