package com.cabral.lucas.nacaotrezeana.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.DebugUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabral.lucas.nacaotrezeana.InfoPlayerActivity;
import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.TicketsPriceActivity;
import com.cabral.lucas.nacaotrezeana.model.NoticeTreze;
import com.cabral.lucas.nacaotrezeana.model.Player;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lucas on 11/01/2017.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.viewHolder>{
    private Context context;
    private List<Player> players;

    public PlayersAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Player player = players.get(position);

        holder.name.setText(player.getName());

         /**
        switch (player.getStatus()){
            case "injured":
                holder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.injured));
                break;
            case "ok":
                holder.status.setVisibility(View.INVISIBLE);
                break;
            case "suspended":
                holder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.card));
                break;
            case "away":
                holder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.out));
                break;
        }
          */

        if(player.getPhoto().equals("http://www.ogol.com.br/img/zerozero_logo_00001.png"))
            player.setPhoto("www.afjbsakdfajsk.com");

        Picasso.with(context).load(player.getPhoto()).fit().error(R.drawable.icon)
                    .placeholder(R.drawable.progress_download).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }


    public void update(List<Player> list) {
        this.players = list;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private ImageView image;
        private ImageView status;

        public viewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.description);
            image = (ImageView) itemView.findViewById(R.id.photoNew);
            status = (ImageView) itemView.findViewById(R.id.iconSituation);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Player player = players.get(getAdapterPosition());

            Intent intent = new Intent(context, InfoPlayerActivity.class);
            intent.putExtra("player", player);
            context.startActivity(intent);
        }
    }
}
