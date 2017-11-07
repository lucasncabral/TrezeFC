package com.cabral.lucas.nacaotrezeana.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cabral.lucas.nacaotrezeana.BettingActivity;
import com.cabral.lucas.nacaotrezeana.ClassificationTableActivity;
import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.TicketsPriceActivity;
import com.cabral.lucas.nacaotrezeana.adapter.CalendarAdapter;
import com.cabral.lucas.nacaotrezeana.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarAdapter newsListAdapter;
    private List<Game> gamesCalendar;
    private RecyclerView recyclerView;
    private ScrollView scroll;
    String todayDate;

    public CalendarFragment() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        todayDate = dateFormat.format(today);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        gamesCalendar = new ArrayList<>();
        gamesCalendar = fetchGames();

        if(gamesCalendar.size() > 0) {
            Game nextGame = gamesCalendar.get(0);
            gamesCalendar.remove(0);


            this.newsListAdapter = new CalendarAdapter(getContext(), gamesCalendar);
            scroll = (ScrollView) v.findViewById(R.id.scroll);

            this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
            // recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            registerForContextMenu(recyclerView);
            this.recyclerView.setAdapter(newsListAdapter);
            this.recyclerView.setNestedScrollingEnabled(false);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.recyclerView.setLayoutManager(linearLayoutManager);


            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            double wi = (double) width / (double) dm.xdpi;
            double hi = (double) height / (double) dm.ydpi;
            double x1 = Math.pow(wi, 2);
            double y1 = Math.pow(hi, 2);
            double screenInches = Math.sqrt(x1 + y1);

            int valueMultiplier = (int) ((height - 888) * 0.10) + 112;

            recyclerView.getLayoutParams().height = valueMultiplier * gamesCalendar.size();
            newsListAdapter.update(gamesCalendar);

            ImageView homeImage = (ImageView) v.findViewById(R.id.home);
            ImageView visitImage = (ImageView) v.findViewById(R.id.visit);
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView time = (TextView) v.findViewById(R.id.time);
            TextView local = (TextView) v.findViewById(R.id.local);

            date.setText(nextGame.getDate());
            time.setText(nextGame.getTime());
            local.setText(nextGame.getLocal());
            loadImage(homeImage, nextGame.getHome());
            loadImage(visitImage, nextGame.getVisit());

            FrameLayout y = (FrameLayout) v.findViewById(R.id.frameLayout);
            y.requestFocus();
        } else {
            RelativeLayout r = (RelativeLayout) v.findViewById(R.id.nextGameLayout);
            r.setVisibility(View.INVISIBLE);
        }

        LinearLayout table = (LinearLayout) v.findViewById(R.id.tableParaibano);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ClassificationTableActivity.class);
                startActivity(i);
            }
        });

        /**
        ImageView tickets = (ImageView) v.findViewById(R.id.priceTickets);
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTicketsPriceFragment();
            }
        });

        ImageView dices = (ImageView) v.findViewById(R.id.dices);
        dices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBettingFragment();
            }
        });

         */
        return v;
    }

    private void loadImage(ImageView imageView, String time) {
        switch (time){
            case "TRZ":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.treze));
                break;
            case "INT":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.internacional));
                break;
            case "CSP":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.csp));
                break;
            case "SER":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.serrano));
                break;

            case "PRB":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.paraiba));
                break;
            case "AUT":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.autoesporte));
                break;
            case "BOT":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.botafogo));
                break;
            case "SOU":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.souza));
                break;

            case "CAM":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.campinense));
                break;
            case "ATL":
                imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.atletico));
                break;

        }

    }

    private void showBettingFragment() {
        Intent intent = new Intent(getContext(), BettingActivity.class);
        getContext().startActivity(intent);
    }

    private void showTicketsPriceFragment() {
        Intent intent = new Intent(getContext(), TicketsPriceActivity.class);
        getContext().startActivity(intent);
    }

    private List<Game> fetchGames() {
        List<Game> notices = new ArrayList<>();

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String JSONMarkers = sharedPref.getString("calendar", null);
        notices = loadNotices(JSONMarkers, notices);
        return notices;
    }

    private List<Game> loadNotices(String JSONMarkers, List<Game> notices){
        if(JSONMarkers != null){
            try {
                JSONObject response = new JSONObject(JSONMarkers);
                JSONArray responsePost = response.getJSONArray("result");
                for (int i = 0; i < responsePost.length(); i++) {
                    JSONObject marked = responsePost.getJSONObject(i);
                    String home = marked.getString("home");
                    String visit = marked.getString("visit");
                    String time = marked.getString("time");
                    String local = marked.getString("local");
                    String date = marked.getString("date");

                    if(compareDate(date)) {
                        notices.add(new Game(home, visit, time, date, local));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return notices;
    }

    private boolean compareDate(String date1) {
        String date2 = todayDate;

        int day1 = Integer.parseInt(date1.substring(0,2));
        int month1 = Integer.parseInt(date1.substring(3,5));
        int year1 = Integer.parseInt(date1.substring(6,10));

        int day2 = Integer.parseInt(date2.substring(0,2));
        int month2 = Integer.parseInt(date2.substring(3,5));
        int year2 = Integer.parseInt(date2.substring(6,10));

        int result = 0;
        if(year1 > year2) {
            result = -1;
        } else if (year1 < year2){
            result = 1;
        } else {
            if(month1 > month2) {
                result = -1;
            } else if (month1 < month2)
                result = 1;
            else {
                if(day1 >= day2) {
                    result = -1;
                } else
                    result = 1;
            }
        }
        if (result == 1){
            return false;
        } else
            return true;

    }


}
