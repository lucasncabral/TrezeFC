package com.cabral.lucas.nacaotrezeana.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabral.lucas.nacaotrezeana.DividerItemDecoration;
import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.adapter.NewsAdapter;
import com.cabral.lucas.nacaotrezeana.model.NoticeTreze;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentNovidades extends Fragment {

    private NewsAdapter newsListAdapter;
    private List<NoticeTreze> notices;
    private RecyclerView recyclerView;

    public FragmentNovidades() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String noInternet = sharedPref.getString("noInternet", "no");
        if(noInternet.equals("yes"))
        return inflater.inflate(R.layout.fragment_no_informations, container, false);
        else {
            View v = inflater.inflate(R.layout.fragment_fragment_novidades, container, false);

            notices = fetchNews();
            this.newsListAdapter = new NewsAdapter(getContext(), notices);

            this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            registerForContextMenu(recyclerView);
            this.recyclerView.setAdapter(newsListAdapter);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.recyclerView.setLayoutManager(linearLayoutManager);

            newsListAdapter.update(notices);
            return v;
        }
    }

    private List<NoticeTreze> fetchNews() {
        List<NoticeTreze> notices = new ArrayList<>();

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String JSONMarkers = sharedPref.getString("notices", null);
        notices = loadNotices(JSONMarkers, notices);
        return notices;
    }

    private List<NoticeTreze> loadNotices(String JSONMarkers, List<NoticeTreze> notices){
        if(JSONMarkers != null){
            try {
                JSONObject response = new JSONObject(JSONMarkers);
                JSONArray responsePost = response.getJSONArray("result");
                for (int i = 0; i < responsePost.length(); i++) {
                    if(i % 4 == 0 && i != 0 && isOnline()){
                        NoticeTreze n = new NoticeTreze(null,null,null,null);
                        n.setType(0);
                        notices.add(n);
                    }

                    JSONObject marked = responsePost.getJSONObject(i);
                    String description;
                    if (marked.getString("description").length() > 50)
                        description = marked.getString("description").substring(0,50) + "...";
                    else
                        description = marked.getString("description");
                    String linkImage = marked.getString("linkImage");
                    String publishedBy = marked.getString("publishedBy");
                    String linkNotice = marked.getString("linkNotice");
                    notices.add(new NoticeTreze(description,linkImage,publishedBy,linkNotice));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return notices;
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
