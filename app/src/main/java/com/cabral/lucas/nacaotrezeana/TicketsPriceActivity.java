package com.cabral.lucas.nacaotrezeana;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class TicketsPriceActivity extends AppCompatActivity {

    public TicketsPriceActivity(){}

    public static TicketsPriceActivity newInstance() {
        TicketsPriceActivity frag = new TicketsPriceActivity();
        return frag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_price);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
