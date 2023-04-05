package com.demo.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {
    @SerializedName("imdb")
    private float kp;

    public Rating(float kp) {
        this.kp = kp;
    }

    public float getKp() {
        return kp;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "kp='" + kp + '\'' +
                '}';
    }
}
