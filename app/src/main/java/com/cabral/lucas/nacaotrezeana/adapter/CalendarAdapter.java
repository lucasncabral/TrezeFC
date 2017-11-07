package com.cabral.lucas.nacaotrezeana.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.model.Game;
import com.cabral.lucas.nacaotrezeana.model.NoticeTreze;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lucas on 11/01/2017.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.viewHolder>{
    private Context context;
    private List<Game> games;

    public CalendarAdapter(Context context, List<Game> notices) {
        this.context = context;
        this.games = notices;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Game game = games.get(position);

        holder.date.setText(game.getDate());
        holder.time.setText(game.getTime());
        loadImage(holder.home, game.getHome());
        loadImage(holder.visit, game.getVisit());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    public void update(List<Game> list) {
        this.games = list;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date;
        private TextView time;
        private ImageView home;
        private ImageView visit;

        public viewHolder(View itemView) {
            super(itemView);

            this.date = (TextView) itemView.findViewById(R.id.date);
            this.time = (TextView) itemView.findViewById(R.id.time);
            this.home = (ImageView) itemView.findViewById(R.id.home);
            this.visit = (ImageView) itemView.findViewById(R.id.visit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }

    private void loadImage(ImageView imageView, String time) {
        switch (time) {
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
}
