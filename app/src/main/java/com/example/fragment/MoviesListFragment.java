package com.example.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.MovieAdapter;
import com.example.cinedome.R;
import com.example.model.Cine;
import com.example.model.MovieShowtime;
import com.example.service.ApiHelper;
import com.example.utilities.Constants;
import com.example.utilities.Utilities;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListFragment extends Fragment implements Callback<Cine>, MovieAdapter.OnMovieClickedListener {

    private RecyclerView recyclerViewMovie;
    private TextView textViewEmpty;
    private Toolbar toolbar;
    private ImageView imageViewCine;
    private View view;
    private Cine cine;
    private TabLayout tabLayoutDays;
    private int selectedTab;
    private List<Date> days;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        recyclerViewMovie = view.findViewById(R.id.recyclerViewMovie);
        textViewEmpty = view.findViewById(R.id.textViewEmpty);
        tabLayoutDays =  view.findViewById(R.id.tabLayoutDays);

        toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        imageViewCine = view.findViewById(R.id.imageViewCine);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ApiHelper.getInstance().getCineApi().getCine().enqueue(this);
    }

    @Override
    public void onResponse(Call<Cine> call, Response<Cine> response) {
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        cine = response.body();
        if (response.isSuccessful()) {
            if (!cine.getMovieShowtimes().isEmpty()) {
                days = Utilities.getMoviesDays(cine.getMovieShowtimes());
                for(Date day : days) {
                    tabLayoutDays.addTab(tabLayoutDays.newTab().setText(Utilities.formatDate(day, Constants.DATE_FORMAT_DD_MM)));
                }
                tabLayoutDays.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        selectedTab = tab.getPosition();
                        updateAdapter(days.get(selectedTab));
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                recyclerViewMovie.setVisibility(View.VISIBLE);
                textViewEmpty.setVisibility(View.GONE);
                tabLayoutDays.getTabAt(selectedTab).select();
                updateAdapter(days.get(selectedTab));

                toolbar.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.CINE, cine);

                    Fragment cineFragment = new CineFragment();
                    cineFragment.setArguments(bundle);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentContainer, cineFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                });

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette palette = Palette.from(bitmap).generate();
                        int lightVibrant = palette.getLightVibrantColor(0x000000);
                        imageViewCine.setImageBitmap(bitmap);
                        toolbar.setBackgroundColor(lightVibrant);
                        view.setBackgroundColor(lightVibrant);
                        view.getBackground().setAlpha(50);
                        tabLayoutDays.setSelectedTabIndicatorColor(lightVibrant);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.get().load(cine.getPlace().getTheater().getPicture().getHref()).into(target);
                view.setTag(target);
            } else {
                recyclerViewMovie.setVisibility(View.GONE);
                textViewEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateAdapter(Date day) {
        List<MovieShowtime> movieShowtimes = Utilities.getMovieShowtimesByDate(cine.getMovieShowtimes(), day);
        recyclerViewMovie.setAdapter(new MovieAdapter(movieShowtimes, day, this, getContext()));
    }

    @Override
    public void onFailure(Call<Cine> call, Throwable t) {
    }

    @Override
    public void onMovieClicked(MovieShowtime movieShowtime) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MOVIE_SHOWTIME, movieShowtime);
        bundle.putSerializable(Constants.CINE_PICTURE, cine.getPlace().getTheater().getPicture());

        Fragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, movieFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onTicketsClicked(MovieShowtime movieShowtime) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MOVIE_SHOWTIME, movieShowtime);
        bundle.putSerializable(Constants.CINE_PICTURE, cine.getPlace().getTheater().getPicture());
        bundle.putSerializable(Constants.MOVIE_DAY, days.get(selectedTab));

        Fragment ticketsFragment = new TicketsFragment();
        ticketsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, ticketsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
