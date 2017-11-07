package com.cabral.lucas.nacaotrezeana;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.cabral.lucas.nacaotrezeana.adapter.ClassificationAdapter;
import com.cabral.lucas.nacaotrezeana.model.NoticeTreze;
import com.cabral.lucas.nacaotrezeana.model.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassificationTableActivity extends AppCompatActivity {

    private List<Time> times = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClassificationAdapter classificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_table);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(getString(R.string.title_classification));
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        times = fetchTeams();
        Collections.sort(times, new Comparator<Time>() {
            @Override
            public int compare(Time time, Time t1) {
                if (time.getPontuation() > t1.getPontuation())
                    return -1;
                else if (time.getPontuation() == t1.getPontuation()) {
                    if(time.getSg() > t1.getSg())
                        return -1;
                    else
                        return 1;
                } else
                    return 1;
            }
        });
        setClassification();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case 16908332:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setClassification() {
        this.classificationAdapter = new ClassificationAdapter(getApplicationContext(), times);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setNestedScrollingEnabled(false);
        registerForContextMenu(recyclerView);
        this.recyclerView.setAdapter(classificationAdapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        classificationAdapter.update(times);
    }

    private List<Time> fetchTeams() {
        List<Time> notices = new ArrayList<>();

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String JSONMarkers = sharedPref.getString("teams", null);
        notices = loadTeams(JSONMarkers, notices);
        return notices;
    }

    private List<Time> loadTeams(String JSONMarkers, List<Time> notices){
        if(JSONMarkers != null){
            try {
                JSONObject response = new JSONObject(JSONMarkers);
                JSONArray responsePost = response.getJSONArray("result");
                for (int i = 0; i < responsePost.length(); i++) {
                    JSONObject marked = responsePost.getJSONObject(i);
                    int pontuation = marked.getInt("pontuation");
                    int tie = marked.getInt("tie");
                    int sg = marked.getInt("sg");
                    int losers = marked.getInt("losers");
                    int id = marked.getInt("id");
                    int victorys = marked.getInt("victorys");
                    String abr = marked.getString("abr");
                    String nome = marked.getString("nome");
                    notices.add(new Time(nome,abr,pontuation,victorys,tie,losers,sg));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return notices;
    }

}
