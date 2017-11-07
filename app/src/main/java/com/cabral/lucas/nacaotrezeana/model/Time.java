package com.cabral.lucas.nacaotrezeana.model;

/**
 * Created by Lucas on 12/01/2017.
 */

public class Time {
    private String nome, abr;
    private int pontuation, victorys, losers, tie, sg;

    public Time(String nome, String abr, int pontuation, int victorys, int tie, int losers, int sg){
        this.nome = nome;
        this.abr = abr;
        this.pontuation = pontuation;
        this.victorys = victorys;
        this.losers = losers;
        this.tie = tie;
        this.sg = sg;
    }



    public String getNome() {
        return nome;
    }

    public int getPontuation() {
        return pontuation;
    }

    public int getVictorys() {
        return victorys;
    }

    public int getLosers() {
        return losers;
    }

    public int getTie() {
        return tie;
    }

    public int getSg() {
        return sg;
    }

    public String getAbr() {
        return abr;
    }

    public String toStringValues() {
        String result = verificaTamanho(pontuation);
        result += pontuation;
        result += verificaTamanho(pontuation);
        result += victorys;
        result += verificaTamanho(victorys);
        result += tie;
        result += verificaTamanho(tie);
        result += losers;
        result += verificaTamanho(losers) + " ";
        result += sg;
        result += verificaTamanho(sg);
        result.substring(0,30);
        return result;
    }

    private String verificaTamanho(int pontuation) {
        int total = 7 - String.valueOf(pontuation).length();
        String result = "";
        for(int i =0; i < total;i++)
            result += " ";
        return result;
    }
}
