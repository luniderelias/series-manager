package com.apolotech.seriesmanager.View.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apolotech.seriesmanager.Model.Movie;
import com.apolotech.seriesmanager.R;
import com.apolotech.seriesmanager.Util.MovieImageUrlBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Movie item);
    }

    public MovieAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.listener = listener;
        this.movies.addAll(movies);
    }

    public void addMovies(List<Movie> movies){
        this.movies.addAll(movies);
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(holder.movieImageUrlBuilder.buildPosterUrl(movies.get(position).posterPath))
                .placeholder(R.drawable.movie_placeholder)
                .into(holder.posterImageView);
        holder.posterImageView.setOnClickListener(v -> listener.onItemClick(movies.get(position)));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

        private final ImageView posterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
