package com.example.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.MovieDayAdapter;
import com.example.cinedome.R;
import com.example.model.MovieShowtime;
import com.example.model.Picture;
import com.example.model.Scr;
import com.example.utilities.Constants;
import com.example.utilities.Utilities;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TicketsFragment extends Fragment {

    private TextView textViewMovieTitle;
    private ImageView imageViewMoviePoster;
    private TextView textViewMovieDirectors;
    private RecyclerView recyclerViewMovieDay;
    private Toolbar toolbar;
    private View view;
    private ImageView imageViewCine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tickets, container, false);
        textViewMovieTitle = view.findViewById(R.id.textViewMovieTitle);
        imageViewMoviePoster = view.findViewById(R.id.imageViewMoviePoster);
        recyclerViewMovieDay = view.findViewById(R.id.recyclerViewMovieDay);
        textViewMovieDirectors = view.findViewById(R.id.textViewMovieDirectors);
        imageViewCine = view.findViewById(R.id.imageViewCine);

        toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        MovieShowtime movieShowtime = (MovieShowtime) bundle.getSerializable(Constants.MOVIE_SHOWTIME);
        Picture picture = (Picture) bundle.getSerializable(Constants.CINE_PICTURE);
        Date day = (Date) bundle.getSerializable(Constants.MOVIE_DAY);

        toolbar.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });
        Picasso.get().load(picture.getHref()).into(imageViewCine);

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette palette = Palette.from(bitmap).generate();
                int lightVibrant = palette.getVibrantColor(0x000000);
                imageViewMoviePoster.setImageBitmap(bitmap);
                toolbar.setBackgroundColor(lightVibrant);
                view.setBackgroundColor(lightVibrant);
                view.getBackground().setAlpha(50);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.get().load(movieShowtime.getOnShow().getMovie().getPoster().getHref()).into(target);
        view.setTag(target);

        textViewMovieTitle.setText(movieShowtime.getOnShow().getMovie().getTitle());
        textViewMovieDirectors.setText(movieShowtime.getOnShow().getMovie().getCastingShort().getDirectors());

        recyclerViewMovieDay.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMovieDay.setAdapter(new MovieDayAdapter(getOrderedScr(movieShowtime.getScr(), day), getContext()));
    }

    private List<Scr> getOrderedScr(List<Scr> scrList, Date day) {
        List<Scr> orderedScr = new ArrayList<Scr>();
        for(Scr scr : scrList) {
            if(day.equals(Utilities.parseDate(scr.getD()))) {
                orderedScr.add(scr);
                scrList.remove(scr);
                orderedScr.addAll(scrList);
                break;
            }
        }
        return orderedScr;
    }

}
