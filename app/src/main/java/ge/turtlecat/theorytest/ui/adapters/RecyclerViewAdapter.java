package ge.turtlecat.theorytest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ge.turtlecat.theorytest.R;
import ge.turtlecat.theorytest.bean.Ticket;

/**
 * Created by Alex on 11/25/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<Ticket> ticketList;

    public RecyclerViewAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    protected static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView ticketQuestionTV;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
