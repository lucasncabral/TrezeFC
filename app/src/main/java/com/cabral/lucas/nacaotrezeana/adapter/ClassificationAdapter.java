package com.cabral.lucas.nacaotrezeana.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.model.Time;

import java.util.List;

/**
 * Created by Lucas on 11/01/2017.
 */

public class ClassificationAdapter extends RecyclerView.Adapter<ClassificationAdapter.viewHolder>{
    private Context context;
    private List<Time> times;

    public ClassificationAdapter(Context context, List<Time> times) {
        this.context = context;
        this.times = times;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.classification_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Time time = times.get(position);

        holder.classification.setText("" + (position + 1));

        holder.values.setText(time.getPontuation() + "");
        holder.values2.setText(time.getVictorys() + "");
        holder.values3.setText(time.getTie() + "");
        holder.values4.setText(time.getLosers() + "");
        holder.values5.setText(time.getSg() + "");

        if(position < 4){
            holder.classification.setBackgroundColor(getContext().getResources().getColor(R.color.classified));
        } else if (position > 7) {
            holder.classification.setBackgroundColor(getContext().getResources().getColor(R.color.desclassified));
        }

        holder.name.setText(time.getNome());
        loadImage(holder.icon, time.getAbr());

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

    @Override
    public int getItemCount() {
        return times.size();
    }


    public void update(List<Time> list) {
        this.times = list;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView classification;
        private TextView name;
        private TextView values, values2,values3,values4,values5;
        private ImageView icon;

        public viewHolder(View itemView) {
            super(itemView);

            classification = (TextView) itemView.findViewById(R.id.colocation);
            name = (TextView) itemView.findViewById(R.id.name);
            values = (TextView) itemView.findViewById(R.id.values);
            values2 = (TextView) itemView.findViewById(R.id.values2);
            values3 = (TextView) itemView.findViewById(R.id.values3);
            values4 = (TextView) itemView.findViewById(R.id.values4);
            values5 = (TextView) itemView.findViewById(R.id.values5);
            icon = (ImageView) itemView.findViewById(R.id.icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
