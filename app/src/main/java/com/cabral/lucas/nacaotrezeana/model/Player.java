package com.cabral.lucas.nacaotrezeana.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    public String name, photo, position, status;
    public int age, gols, yellowCards,jogos, minutesPlay;

    public Player(String name, String photo, String position, String status, int age){
        this.name = name;
        this.photo = photo;
        this.position = position;
        this.status = status;
        this.age = age;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getJogos() {
        return jogos;
    }

    public Player(String name, String photo, String position, String status, int age, int gols, int yellowCards, int jogos, int minutesPlay){
        this.name = name;
        this.photo = photo;
        this.position = position;
        this.jogos = jogos;
        this.status = status;
        this.age = age;
        this.gols = gols;
        this.yellowCards = yellowCards;
        this.minutesPlay = minutesPlay;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    public int getGols() {
        return gols;
    }

    public int getMinutesPlay() {
        return minutesPlay;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public String getStatus() {
        return status;
    }

    protected Player(Parcel in){
        name = in.readString();
        photo = in.readString();
        position = in.readString();
        status = in.readString();
        age = in.readInt();
        gols = in.readInt();
        yellowCards = in.readInt();
        jogos = in.readInt();
        minutesPlay = in.readInt();
    }

    public static final Parcelable.Creator<Player>
            CREATOR = new Parcelable.Creator<Player>() {

        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(position);
        dest.writeString(status);
        dest.writeInt(age);
        dest.writeInt(gols);
        dest.writeInt(yellowCards);
        dest.writeInt(jogos);
        dest.writeInt(minutesPlay);
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
