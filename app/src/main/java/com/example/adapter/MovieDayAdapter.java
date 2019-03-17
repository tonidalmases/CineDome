package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.cinedome.R;
import com.example.model.Scr;
import com.example.model.T;
import com.example.utilities.Constants;
import com.example.utilities.Utilities;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDayAdapter extends RecyclerView.Adapter<MovieDayAdapter.MovieDayViewHolder> {

    private List<Scr> scrList;
    private Context context;

    public MovieDayAdapter(List<Scr> scrList, Context context) {
        this.scrList = scrList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieDayAdapter.MovieDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_day, parent, false);
        return new MovieDayAdapter.MovieDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDayAdapter.MovieDayViewHolder holder, int position) {
        Scr scr = scrList.get(position);
        if(position == 0) {
            holder.viewSeparator.setVisibility(View.GONE);
        }
        holder.textViewItemDay.setText(Utilities.formatDate(scr.getD(), Constants.DATE_FORMAT_DD_MM));

        GridLayout gridLayout = holder.itemView.findViewById(R.id.gridLayoutMovieTime);

        if(gridLayout != null) {
            for (T t : scr.getT()) {
                Button btn = new Button(context);
                btn.setText(t.getName());
                btn.setOnClickListener(v -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(t.getTk()));
                    context.startActivity(i);
                });
                gridLayout.addView(btn);
            }
        }

    }

    @Override
    public int getItemCount() {
        return scrList.size();
    }

    static class MovieDayViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemDay;
        View viewSeparator;

        MovieDayViewHolder(View view) {
            super(view);
            textViewItemDay = view.findViewById(R.id.textViewItemDay);
            viewSeparator = view.findViewById(R.id.viewSeparator);
        }
    }

}
