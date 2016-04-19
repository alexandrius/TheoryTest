package ge.turtlecat.theorytest.ui.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.ui.tools.DescriptionFragmentStatus;

/**
 * A simple {@link Fragment} subclass.
 */
public class TiketDescriptionFragment extends Fragment {
    TextView description_textview;

    public TiketDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tiket_description, container, false);
        Animation animationFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        FrameLayout playerlayout = (FrameLayout) v.findViewById(R.id.description_fragment_layout);
        playerlayout.startAnimation(animationFadeIn);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);

        ////description
        description_textview = (TextView) v.findViewById(R.id.description_textview);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = preferences.getString("Description", "");
        if (!name.equalsIgnoreCase("")) {
            description_textview.setText(name);
        }
////

        playerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animationFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
                FrameLayout playerlayout = (FrameLayout) v.findViewById(R.id.description_fragment_layout);
                playerlayout.startAnimation(animationFadeIn);
                playerlayout.setVisibility(View.INVISIBLE);
                new DescriptionFragmentStatus().setStatus(true);
            }
        });
        return v;
    }

}