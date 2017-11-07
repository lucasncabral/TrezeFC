package com.cabral.lucas.nacaotrezeana;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cabral.lucas.nacaotrezeana.model.Player;

public class InfoPlayerActivity extends AppCompatActivity {
    Player p;
    public InfoPlayerActivity(){}

    public static InfoPlayerActivity newInstance() {
        InfoPlayerActivity frag = new InfoPlayerActivity();
        return frag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_player);
        p =  getIntent().getExtras().getParcelable("player");

        TextView name = (TextView) findViewById(R.id.lbl_name);
        name.setText(p.getName());

        TextView position = (TextView) findViewById(R.id.lbl_position);
        position.setText(p.getPosition());

        TextView gols = (TextView) findViewById(R.id.lbl_gols);
        gols.setText("Gols\n" + p.getGols());

        TextView cards = (TextView) findViewById(R.id.lbl_cards);
        cards.setText("Cartões\n" + p.getYellowCards());

        TextView games = (TextView) findViewById(R.id.lbl_games);
        games.setText("Jogos\n" + p.getJogos());

        TextView minutes = (TextView) findViewById(R.id.lbl_minutes);
        minutes.setText("Minutos\n" + p.getMinutesPlay());

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
