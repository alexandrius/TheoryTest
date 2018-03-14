package ge.turtlecat.theorytest.ui.components;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.AttributeSet;

import ge.turtlecat.theorytest.ui.fragments.TicketFragment;
import ge.turtlecat.theorytest.ui.tm.TicketManager;

/**
 * Created by Alex on 11/21/2015.
 */
public class TicketPager extends CustomViewPager {
    TicketFragment[] fragments;

    public TicketPager(Context context) {
        super(context);
        if (isInEditMode()) return;
        init();
    }

    public TicketPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        init();

    }

    public TicketFragment getCurrentFragment() {
        return fragments[getCurrentItem()];
    }

    private void init() {
        fragments = new TicketFragment[TicketManager.getInstance().getCurrentTickets().size()];

        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = (TicketFragment) new TicketFragment()
                    .setIntArgument("ticket", i)
                    .commitBundle();
        }
    }


    public void setFM(FragmentManager fm) {
        setAdapter(new TicketPagerAdapter(fm));
    }


    private class TicketPagerAdapter extends FragmentStatePagerAdapter {

        public TicketPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
