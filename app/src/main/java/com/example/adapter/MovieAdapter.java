package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinedome.R;
import com.example.model.MovieShowtime;
import com.example.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieShowtime> movieShowtimes;
    private OnMovieClickedListener listener;
    private Context context;
    private Date day;

    public MovieAdapter(List<MovieShowtime> movieShowtimes, Date day, OnMovieClickedListener onMovieClickedListener, Context context) {
        this.movieShowtimes = movieShowtimes;
        this.listener = onMovieClickedListener;
        this.context = context;
        this.day = day;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieShowtime movieShowtime = movieShowtimes.get(position);

        if(movieShowtime.getOnShow().getMovie().getCastingShort() != null) {
            holder.textViewItemDirectors.setText(movieShowtime.getOnShow().getMovie().getCastingShort().getDirectors());
        } else {
            holder.textViewItemDirectors.setVisibility(View.GONE);
        }

        if(movieShowtime.getOnShow().getMovie().getTitle() != null) {
            holder.textViewItemTitle.setText(movieShowtime.getOnShow().getMovie().getTitle());
        } else {
            holder.textViewItemTitle.setVisibility(View.GONE);
        }

        if(movieShowtime.getOnShow().getMovie().getGenre() != null) {
            holder.textViewItemGenre.setText(Utilities.getGenre(movieShowtime.getOnShow().getMovie().getGenre()));
        } else {
            holder.textViewItemGenre.setVisibility(View.GONE);
        }

        if(movieShowtime.getOnShow().getMovie().getPoster() != null) {
            Picasso.get().load(movieShowtime.getOnShow().getMovie().getPoster().getHref()).into(holder.imageViewItemPoster);
        } else {
            holder.imageViewItemPoster.setVisibility(View.GONE);
        }

        if(movieShowtime.getOnShow().getMovie().getRuntime() != null) {
            holder.textViewItemDuration.setText(Utilities.getDuration(movieShowtime.getOnShow().getMovie().getRuntime()) + " " + context.getResources().getString(R.string.minutes));
        } else {
            holder.textViewItemDuration.setVisibility(View.GONE);
        }

        if(movieShowtime.getScr() != null) {
            holder.textViewItemTimes.setText(Utilities.getTimes(movieShowtime.getScr(), day));
            holder.buttonItemTickets.setOnClickListener(v -> listener.onTicketsClicked(movieShowtime));
        } else {
            holder.textViewItemTimes.setVisibility(View.GONE);
            holder.buttonItemTickets.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onMovieClicked(movieShowtime));
    }

    @Override
    public int getItemCount() {
        return movieShowtimes.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemTitle;
        TextView textViewItemGenre;
        ImageView imageViewItemPoster;
        TextView textViewItemDuration;
        TextView textViewItemTimes;
        Button buttonItemTickets;
        TextView textViewItemDirectors;

        MovieViewHolder(View view) {
            super(view);
            textViewItemTitle = view.findViewById(R.id.textViewItemTitle);
            textViewItemGenre = view.findViewById(R.id.textViewItemGenre);
            imageViewItemPoster = view.findViewById(R.id.imageViewItemPoster);
            textViewItemDuration = view.findViewById(R.id.textViewItemDuration);
            textViewItemTimes = view.findViewById(R.id.textViewItemTimes);
            buttonItemTickets = view.findViewById(R.id.buttonItemTickets);
            textViewItemDirectors = view.findViewById(R.id.textViewItemDirectors);
        }
    }

    public interface OnMovieClickedListener {
        void onMovieClicked(MovieShowtime movieShowtime);
        void onTicketsClicked(MovieShowtime movieShowtime);
    }

}
