package com.cabral.lucas.nacaotrezeana.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cabral.lucas.nacaotrezeana.CrowdActivity;
import com.cabral.lucas.nacaotrezeana.OrganizationActivity;
import com.cabral.lucas.nacaotrezeana.R;
import com.cabral.lucas.nacaotrezeana.TeamActivity;
import com.cabral.lucas.nacaotrezeana.TrophyRoomActivity;

public class TrezeFragment extends Fragment {
    private static final long TIME_OUT = 3000;
    private boolean sound = false;
    private MediaPlayer mp;
    private ProgressDialog dialog;

    public TrezeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_treze, container, false);
        LinearLayout galeria = (LinearLayout) v.findViewById(R.id.btn_galeria);

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGalery();
            }
        });

        LinearLayout organizacao = (LinearLayout) v.findViewById(R.id.btn_organizacao);

        organizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrganization();
            }
        });

        LinearLayout torcida = (LinearLayout) v.findViewById(R.id.btn_torcida);

        torcida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCrowd();
            }
        });

        LinearLayout history = (LinearLayout) v.findViewById(R.id.btn_history);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistory();
            }
        });


        LinearLayout team = (LinearLayout) v.findViewById(R.id.btn_team);

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTeam();
            }
        });

        LinearLayout hino = (LinearLayout) v.findViewById(R.id.hino_treze);
        hino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sound) {
                    sound = true;
                    dialog = ProgressDialog.show(getActivity(), "",
                            "Carregando...", true);

                    dialog.show();
                    mp = MediaPlayer.create(getActivity(), R.raw.hino_treze);
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }

                    });
                    mp.start();
                    dismissDialog();
                } else {
                    sound = false;
                    try {
                        mp.stop();
                    } catch (Exception ex){
                        Log.d("", "Ja tava tocando");
                    }
                }
            }
        });
        return v;
    }

    private void showOrganization() {
        Intent intent = new Intent(getContext(), OrganizationActivity.class);
        intent.putExtra("history", false);
        getContext().startActivity(intent);
    }

    private void showHistory() {
        Intent intent = new Intent(getContext(), OrganizationActivity.class);
        intent.putExtra("history", true);
        getContext().startActivity(intent);
    }

    private void dismissDialog() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, TIME_OUT);
    }

    private void showGalery() {
        Intent intent = new Intent(getContext(), TrophyRoomActivity.class);
        getContext().startActivity(intent);
    }

    private void showTeam() {
        Intent intent = new Intent(getContext(), TeamActivity.class);
        getContext().startActivity(intent);
        //getActivity().overridePendingTransition(R.anim.fade_in_2, 0);
    }

    private void showCrowd(){
        Intent intent = new Intent(getContext(), CrowdActivity.class);
        getContext().startActivity(intent);

    }
}