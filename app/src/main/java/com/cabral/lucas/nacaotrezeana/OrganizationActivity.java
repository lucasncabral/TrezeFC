package com.cabral.lucas.nacaotrezeana;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

public class OrganizationActivity extends AppCompatActivity {

    private boolean history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        history = extras.getBoolean("history");

        if(!history)
            setContentView(R.layout.activity_organization);
        else
            setContentView(R.layout.activity_history);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        if(!history)
            actionbar.setTitle(getString(R.string.title_organization));
        else
            actionbar.setTitle(getString(R.string.title_history));

        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
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

}
