
package com.example.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cine implements Serializable {

    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("movieShowtimes")
    @Expose
    private List<MovieShowtime> movieShowtimes = null;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<MovieShowtime> getMovieShowtimes() {
        return movieShowtimes;
    }

    public void setMovieShowtimes(List<MovieShowtime> movieShowtimes) {
        this.movieShowtimes = movieShowtimes;
    }

}
