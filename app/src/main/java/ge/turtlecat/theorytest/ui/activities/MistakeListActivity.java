package ge.turtlecat.theorytest.ui.activities;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ge.turtlecat.theorytest.R;

/**
 * Created by Alex on 11/25/2015.
 */
public class MistakeListActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mistake_list;
    }

    @Override
    protected void onCreate() {
        recyclerView = (RecyclerView) findViewById(R.id.mistake_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }
}
