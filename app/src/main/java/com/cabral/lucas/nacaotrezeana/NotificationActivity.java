package com.cabral.lucas.nacaotrezeana;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class NotificationActivity extends AppCompatActivity {
    private static String SERVER_URI = "https://es1-server.herokuapp.com";
    private Boolean[] options = {false, false, false, false};
    private SharedPreferences sharedPref;
    private AsyncHttpClient client;

    public NotificationActivity(){
    }

    public static NotificationActivity newInstance(){
        NotificationActivity frag = new NotificationActivity();
        return frag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        client = new AsyncHttpClient();
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        options[0] = sharedPref.getBoolean("notificationGoal", true);
        options[1] = sharedPref.getBoolean("notificationMatch", true);
        options[2] = sharedPref.getBoolean("notificationPenaltie", true);
        options[3] = sharedPref.getBoolean("notificationChanges", true);


        Switch goals = (Switch) findViewById(R.id.switch1);
        goals.setChecked(options[0]);

        goals.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    options[0] = !options[0];
            }
        });

        Switch match = (Switch) findViewById(R.id.switch2);
        match.setChecked(options[1]);
        match.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                options[1] = !options[1];
            }
        });
        Switch penalties = (Switch) findViewById(R.id.switch3);
        penalties.setChecked(options[2]);
        penalties.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                options[2] = !options[2];
            }
        });
        Switch changes = (Switch) findViewById(R.id.switch4);
        changes.setChecked(options[3]);
        changes.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                options[3] = !options[3];
            }
        });

        Button apply = (Button) findViewById(R.id.button_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOptions();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    public void saveOptions(){
        setToken();
        onBackPressed();
    }

    private void setToken() {
        String getMarkersUrl = SERVER_URI + "/updateToken";

        client.setConnectTimeout(6000);
        RequestParams requestParams = new RequestParams();
        String token = FirebaseInstanceId.getInstance().getToken();
        requestParams.put("token", token);
        requestParams.put("notificationGoal", options[0]);
        requestParams.put("notificationMatch",  options[1]);
        requestParams.put("notificationPenaltie",  options[2]);
        requestParams.put("notificationChanges",  options[3]);
        requestParams.setUseJsonStreamer(true);
        client.post(getMarkersUrl, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplication(), "Verifique sua conexão", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("notificationGoal", options[0]);
                editor.putBoolean("notificationMatch", options[1]);
                editor.putBoolean("notificationPenaltie", options[2]);
                editor.putBoolean("notificationChanges", options[3]);
                editor.apply();
            }
        });
    }
}
