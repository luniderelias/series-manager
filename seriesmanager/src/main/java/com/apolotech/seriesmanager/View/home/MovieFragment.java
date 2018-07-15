package com.apolotech.seriesmanager.View.home;


import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolotech.seriesmanager.Model.Cache;
import com.apolotech.seriesmanager.Model.Genre;
import com.apolotech.seriesmanager.Model.Movie;
import com.apolotech.seriesmanager.Model.Season;
import com.apolotech.seriesmanager.R;
import com.apolotech.seriesmanager.Service.MovieService;
import com.apolotech.seriesmanager.Util.EndlessRecyclerViewScrollListener;
import com.apolotech.seriesmanager.Util.MovieImageUrlBuilder;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.fragment_movie)
public class MovieFragment extends Fragment {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.posterImageView)
    ImageView posterImageView;

    @ViewById(R.id.backdropImageView)
    ImageView backdropImageView;

    @ViewById(R.id.titleTextView)
    TextView titleTextView;

    @ViewById(R.id.descriptionTextView)
    TextView descriptionTextView;

    @ViewById(R.id.genresTextView)
    TextView genresTextView;

    @ViewById(R.id.releaseDateTextView)
    TextView releaseDateTextView;


    @Bean
    MovieService movieService;


    LinearLayoutManager layoutManager;

    Movie movie;
    static Season season;
    SeasonAdapter seasonAdapter;
    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

    @AfterViews
    void afterViews() {
        movie = ((HomeActivity_) getActivity()).movie;
        configureRecyclerView();
        loadData();
    }

    private void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(
                getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Background
    void loadData() {
        movieService.getMovie(movie.id)
                .onErrorResumeNext(response -> {
                }).subscribe(response -> {
            movie = response;
            setSeasonsAdapter();
            loadImages();
            setTexts();
        }).isDisposed();
    }

    void setSeasonsAdapter() {
        seasonAdapter = new SeasonAdapter(getActivity(), movie.seasons,
                item -> {
                    season = item;
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_container, new MovieFragment_(), "MovieFragment")
                            .addToBackStack("MovieFragment")
                            .commit();
                });
        recyclerView.setAdapter(seasonAdapter);
    }

    @UiThread
    void loadImages() {
        Picasso.get()
                .load(movieImageUrlBuilder.buildPosterUrl(movie.posterPath))
                .placeholder(R.drawable.movie_placeholder)
                .into(posterImageView);
        Picasso.get()
                .load(movieImageUrlBuilder.buildBackdropUrl(movie.backdropPath))
                .placeholder(R.drawable.movie_placeholder)
                .into(backdropImageView);
    }

    @UiThread
    void setTexts() {
        if (movie.name != null)
            titleTextView.setText(movie.name);
        if (movie.genres != null)
            genresTextView.setText(getGendersText());
        if (movie.releaseDate != null)
            releaseDateTextView.setText(movie.releaseDate.split("-")[0]);
        if (movie.overview != null)
            descriptionTextView.setText(movie.overview);
    }

    private StringBuilder getGendersText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : movie.genres) {
            stringBuilder.append(genre.name + "  ");
        }
        return stringBuilder;
    }

}
