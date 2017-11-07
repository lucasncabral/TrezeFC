package com.cabral.lucas.nacaotrezeana;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;


public class SplashActivity extends Activity {
    private static final long SPLASH_TIME_OUT = 7978;
    private static String SERVER_URI = "https://es1-server.herokuapp.com";
    private MediaPlayer mp;
    private SharedPreferences sharedPref;
    private AsyncHttpClient client;
    private boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SERVER_URI = getString(R.string.server_uri);
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        client = new AsyncHttpClient();

        sound();

        ImageView myImageView= (ImageView)findViewById(R.id.imgLogo);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        myImageView.startAnimation(myFadeInAnimation);

        getNotices();
        getTeams();
        getCalendar();



        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        firstTime = sharedPref.getBoolean("firstTime", true);
        if(!firstTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openLoginScreen();
                }
            }, SPLASH_TIME_OUT);
        } else {
            setToken();
        }

        getPlayers();

    }

    private void setToken() {
        String getMarkersUrl = SERVER_URI + "/updateToken?";
        client.setConnectTimeout(6000);
        String token = FirebaseInstanceId.getInstance().getToken();
        getMarkersUrl += "token=" + token;
        // requestParams.put("token", token);
        getMarkersUrl += "&notificationGoal=true";
        getMarkersUrl += "&notificationMatch=true";
        getMarkersUrl += "&notificationPenaltie=true";
        getMarkersUrl += "&notificationChanges=true";
        /**
        requestParams.put("notificationGoal", "true");
        requestParams.put("notificationMatch", "true");
        requestParams.put("notificationPenaltie", "true");
        requestParams.put("notificationChanges", "true");
        requestParams.setUseJsonStreamer(true);
        */
         client.post(getMarkersUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Toast.makeText(getApplication(), "Error to update informations", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject timeline) {
            }
        });
    }

    private void getNotices(){
        String getMarkersUrl = SERVER_URI + "/getNotices";
        client.setConnectTimeout(6000);
        client.get(getMarkersUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject timeline) {
                saveMarkers(timeline.toString(), "notices");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers, throwable, response);
                Toast.makeText(SplashActivity.this, getString(R.string.error_make_request), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openLoginScreen() {
        soundStop();
        Intent i;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("firstTime", false);
        editor.apply();
        i = new Intent(SplashActivity.this, MainActivity.class);

        startActivity(i);
        finish();
    }

    private void saveMarkers(String timeLine, String result) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(result, timeLine);
        editor.apply();
    }

    private void soundStop() {
        mp.stop();
    }

    void sound(){
        mp = MediaPlayer.create(SplashActivity.this, R.raw.ole_galo);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });
        mp.start();
    }

    public void getTeams() {
        String getMarkersUrl = SERVER_URI + "/getTeams";
        client.setConnectTimeout(6000);
        client.get(getMarkersUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject timeline) {
                saveMarkers(timeline.toString(), "teams");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers, throwable, response);
                Toast.makeText(SplashActivity.this, getString(R.string.error_make_request), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCalendar() {
        String getMarkersUrl = SERVER_URI + "/getGames";
        client.setConnectTimeout(6000);
        client.get(getMarkersUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject timeline) {
                saveMarkers(timeline.toString(), "calendar");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers, throwable, response);
                Toast.makeText(SplashActivity.this, getString(R.string.error_make_request), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPlayers() {
        String getMarkersUrl = SERVER_URI + "/getPlayers";
        client.setConnectTimeout(6000);
        client.get(getMarkersUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject timeline) {
                saveMarkers(timeline.toString(), "players");
                if(firstTime){
                    openLoginScreen();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers, throwable, response);
                Toast.makeText(SplashActivity.this, getString(R.string.error_make_request), Toast.LENGTH_LONG).show();
                if(firstTime){
                    openLoginScreen();
                }
            }
        });
    }
}
