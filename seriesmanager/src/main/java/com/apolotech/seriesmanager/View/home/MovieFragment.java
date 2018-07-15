package com.apolotech.seriesmanager.View.home;


import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolotech.seriesmanager.Model.Cache;
import com.apolotech.seriesmanager.Model.Genre;
import com.apolotech.seriesmanager.Model.Movie;
import com.apolotech.seriesmanager.Model.Season;
import com.apolotech.seriesmanager.R;
import com.apolotech.seriesmanager.Service.MovieService;
import com.apolotech.seriesmanager.Util.DatabaseHelper;
import com.apolotech.seriesmanager.Util.EndlessRecyclerViewScrollListener;
import com.apolotech.seriesmanager.Util.MovieImageUrlBuilder;
import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

@EFragment(R.layout.fragment_movie)
public class MovieFragment extends Fragment {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.posterImageView)
    ImageView posterImageView;

    @ViewById(R.id.backdropImageView)
    ImageView backdropImageView;

    @ViewById(R.id.addImageView)
    ImageView addImageView;

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

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Movie, Integer> movieDao;

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

    @Click(R.id.addImageView)
    void addItem() {
        saveSeries();
    }

    @Background
    void saveSeries() {
        try {
            movieDao.createOrUpdate(movie);
            HomeActivity_.myListEnabled = true;
            setAddImageView(R.drawable.ic_added);
            Snackbar
                    .make(Objects.requireNonNull(getView()),
                            R.string.serie_added,
                            Snackbar.LENGTH_SHORT)
                    .setActionTextColor(getResources().getColor(R.color.light_blue))
                    .setAction(R.string.undo, view -> {
                        try {
                            movieDao.delete(movie);
                            setAddImageView(R.drawable.ic_add);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configureRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(
                getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
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
        if(HomeActivity_.myListEnabled)
            setAddImageView(R.drawable.ic_added);
        else
            setAddImageView(R.drawable.ic_add);
    }

    @UiThread
    void setAddImageView(Integer resId){
        addImageView.setImageDrawable(getActivity()
                .getResources()
                .getDrawable(resId));
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
