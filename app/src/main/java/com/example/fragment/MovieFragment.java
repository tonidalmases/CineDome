package com.example.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cinedome.R;
import com.example.model.Movie;
import com.example.model.MovieLink;
import com.example.model.MovieShowtime;
import com.example.model.Picture;
import com.example.utilities.Constants;
import com.example.utilities.Utilities;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

public class MovieFragment extends Fragment {

    private TextView textViewMovieTitle;
    private TextView textViewMovieDirectors;
    private TextView textViewMovieActors;
    private TextView textViewMovieGenre;
    private TextView textViewMovieDurationTitle;
    private TextView textViewMovieDuration;
    private TextView textViewMovieRating;
    private GridLayout gridLayoutMovieRating;
    private RatingBar ratingBarMoviePress;
    private RatingBar ratingBarMovieUsers;
    private ImageView imageViewMoviePoster;
    private ImageView imageViewCine;
    private GridLayout gridLayoutMovieLinks;
    private Button buttonMovieUrl;

    private Toolbar toolbar;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        textViewMovieTitle = view.findViewById(R.id.textViewMovieTitle);
        textViewMovieDirectors = view.findViewById(R.id.textViewMovieDirectors);
        textViewMovieActors = view.findViewById(R.id.textViewMovieActors);
        textViewMovieGenre = view.findViewById(R.id.textViewMovieGenre);
        textViewMovieDurationTitle = view.findViewById(R.id.textViewMovieDurationTitle);
        textViewMovieDuration = view.findViewById(R.id.textViewMovieDuration);
        textViewMovieRating = view.findViewById(R.id.textViewMovieRating);
        gridLayoutMovieRating = view.findViewById(R.id.gridLayoutMovieRating);
        ratingBarMoviePress = view.findViewById(R.id.ratingBarMoviePress);
        ratingBarMovieUsers = view.findViewById(R.id.ratingBarMovieUsers);
        imageViewMoviePoster = view.findViewById(R.id.imageViewMoviePoster);
        imageViewCine = view.findViewById(R.id.imageViewCine);
        gridLayoutMovieLinks = view.findViewById(R.id.gridLayoutMovieLinks);

        toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        MovieShowtime movieShowtime = (MovieShowtime) bundle.getSerializable(Constants.MOVIE_SHOWTIME);
        Movie movie = movieShowtime.getOnShow().getMovie();

        Picture picture = (Picture) bundle.getSerializable(Constants.CINE_PICTURE);

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
        Picasso.get().load(movie.getPoster().getHref()).into(target);
        view.setTag(target);

        textViewMovieTitle.setText(movie.getTitle());

        if(movie.getCastingShort() != null) {
            textViewMovieDirectors.setText(movie.getCastingShort().getDirectors());
            textViewMovieActors.setText(movie.getCastingShort().getActors());
        } else {
            textViewMovieDirectors.setVisibility(View.GONE);
            textViewMovieActors.setVisibility(View.GONE);
        }

        if(movie.getGenre() != null) {
            textViewMovieGenre.setText(Utilities.getGenre(movie.getGenre()));
        } else {
            textViewMovieGenre.setVisibility(View.GONE);
        }

        if(movie.getRuntime() != null) {
            textViewMovieDuration.setText(Utilities.getDuration(movie.getRuntime()) + " " + getResources().getString(R.string.minutes));
        } else {
            textViewMovieDurationTitle.setVisibility(View.GONE);
            textViewMovieDuration.setVisibility(View.GONE);
        }

        if(movie.getStatistics().getPressRating() != null || movie.getStatistics().getUserRating() != null) {
            if (movie.getStatistics().getPressRating() != null) {
                ratingBarMoviePress.setRating(movie.getStatistics().getPressRating().floatValue());
            }
            if (movie.getStatistics().getUserRating() != null) {
                ratingBarMovieUsers.setRating(movie.getStatistics().getUserRating().floatValue());
            }
        } else {
            textViewMovieRating.setVisibility(View.GONE);
            gridLayoutMovieRating.setVisibility(View.GONE);
        }

        if(movie.getTrailer() != null) {
            Button btn = new Button(getContext());
            btn.setText(getActivity().getString(R.string.trailer));
            btn.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(movie.getTrailer().getHref()));
                startActivity(i);
            });
            gridLayoutMovieLinks.addView(btn);
        }

        if(movie.getLink() != null) {
            int j = 1;
            for(MovieLink l : movie.getLink()) {
                Button btn = new Button(getContext());
                btn.setText(getActivity().getString(R.string.weblink) + " " + j);
                btn.setOnClickListener(v -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(l.getHref()));
                    startActivity(i);
                });
                gridLayoutMovieLinks.addView(btn);
                j++;
            }
        }

    }

}
