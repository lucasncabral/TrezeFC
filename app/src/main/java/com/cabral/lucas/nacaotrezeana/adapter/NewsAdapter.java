package com.cabral.lucas.nacaotrezeana.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.model.NoticeTreze;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<NoticeTreze> notices;

    public NewsAdapter(Context context, List<NoticeTreze> notices) {
        this.context = context;
        this.notices = notices;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_card, parent, false);
        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType){
            case 0:{
                view = inflater.inflate(R.layout.list_item_admob, parent, false);
                viewHolder = new ViewHolderAdMob(view);
                break;
            }
            default :{
                view = LayoutInflater.from(context).inflate(R.layout.news_card, parent, false);
                viewHolder = new myViewHolder(view);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoticeTreze notice = notices.get(position);

        if(getItemViewType(position) > 0){
            myViewHolder viewHolder  =  (myViewHolder) holder;
            viewHolder .description.setText(notice.getDescription());
            switch (notice.getPublishedBy()) {
                case "ge":
                    viewHolder .iconPublish.setImageDrawable(context.getResources().getDrawable(R.drawable.geicon));
                    break;
                case "ig":
                    viewHolder .iconPublish.setImageDrawable(context.getResources().getDrawable(R.drawable.instagramicon));
                    break;
                case "vt":
                    viewHolder .iconPublish.setImageDrawable(context.getResources().getDrawable(R.drawable.vticon));
                    break;
                case "yt":
                    viewHolder .iconPublish.setImageDrawable(context.getResources().getDrawable(R.drawable.yticon));
                    break;
                case "pbe":
                    viewHolder .iconPublish.setImageDrawable(context.getResources().getDrawable(R.drawable.pbeicon));
                    break;
                case "trz":
                    viewHolder.iconPublish.setImageDrawable(context.getResources().getDrawable(R.drawable.trezeicon));
                    break;
            }


            final String linkNotice = notice.getLinkNotice();
            viewHolder.shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startShare(linkNotice);
                }
            });

            Log.d("ImageNotice - Antes", notice.getLinkImage());
            if (notice.getLinkImage() == null || notice.getLinkImage().equals("")) {
                notice.setLinkImage("www.ajshfdajsfas.com");
            }

            Log.d("ImageNotice - Depois", notice.getLinkImage());
            Picasso.with(context).load(notice.getLinkImage()).fit().error(R.drawable.icon)
                    .placeholder(R.drawable.progress_download).into(viewHolder.image);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return notices.get(position).getType();
    }


    @Override
    public int getItemCount() {
        return notices.size();
    }


    public void update(List<NoticeTreze> list) {
        this.notices = list;
        notifyDataSetChanged();
    }

    public void startShare(String linkNotice){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nação Trezeana");
            String sAux = " --- Compartilhado através de Nação Trezeana --- \n\n";
            sAux += linkNotice;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Compartilhar"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView description;
        private ImageView image;
        private ImageView iconPublish;
        private ImageView shareIcon;

        public myViewHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.description);
            image = (ImageView) itemView.findViewById(R.id.photoNew);
            iconPublish = (ImageView) itemView.findViewById(R.id.iconPublish);
            shareIcon = (ImageView) itemView.findViewById(R.id.sharePublish);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            NoticeTreze notice = notices.get(getAdapterPosition());

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notice.getLinkNotice()));
            context.startActivity(browserIntent);
        }
    }

    public static class ViewHolderAdMob extends RecyclerView.ViewHolder {
        public AdView mAdView;

        public ViewHolderAdMob(View view) {
            super(view);
            /**
            mAdView = (AdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        */
            AdView mAdView = (AdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}
