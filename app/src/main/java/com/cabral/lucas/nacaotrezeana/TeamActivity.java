package com.cabral.lucas.nacaotrezeana;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.cabral.lucas.nacaotrezeana.adapter.NewsAdapter;
import com.cabral.lucas.nacaotrezeana.adapter.PlayersAdapter;
import com.cabral.lucas.nacaotrezeana.model.Game;
import com.cabral.lucas.nacaotrezeana.model.NoticeTreze;
import com.cabral.lucas.nacaotrezeana.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    private PlayersAdapter playersAdapter;
    private List<Player> players;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        players = fetchPlayers();

        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player player, Player t1) {
                if (player.getMinutesPlay() > t1.getMinutesPlay())
                    return -1;
                return 1;
            }
        });
        this.playersAdapter = new PlayersAdapter(this, players);

        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        registerForContextMenu(recyclerView);
        this.recyclerView.setAdapter(playersAdapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(linearLayoutManager);

        playersAdapter.update(players);
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

    private List<Player> fetchPlayers() {
        List<Player> notices = new ArrayList<>();

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String JSONMarkers = sharedPref.getString("players", null);
        notices = loadNotices(JSONMarkers, notices);
        return notices;
    }

    private List<Player> loadNotices(String JSONMarkers, List<Player> notices){
        if(JSONMarkers != null){
            try {
                JSONObject response = new JSONObject(JSONMarkers);
                JSONArray responsePost = response.getJSONArray("result");
                for (int i = 0; i < responsePost.length(); i++) {
                    JSONObject marked = responsePost.getJSONObject(i);
                    String name = marked.getString("name");
                    String photo = marked.getString("photo");
                    String position = marked.getString("position");
                    if(position == null|| position.equals("null"))
                        position = "";
                    int gols = marked.getInt("gols");
                    int yellowCards = marked.getInt("yellowCards");
                    int jogos = marked.getInt("jogos");
                    int minutesPlay = marked.getInt("minutesPlay");
                    notices.add(new Player( name,  photo,  position,  "ok",  30,  gols,  yellowCards,  jogos,  minutesPlay));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return notices;
    }

}
