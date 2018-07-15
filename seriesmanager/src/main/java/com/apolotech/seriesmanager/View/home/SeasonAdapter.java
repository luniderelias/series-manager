package com.apolotech.seriesmanager.View.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolotech.seriesmanager.Model.Movie;
import com.apolotech.seriesmanager.Model.Season;
import com.apolotech.seriesmanager.R;
import com.apolotech.seriesmanager.Util.MovieImageUrlBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private List<Season> seasons = new ArrayList<>();
    private final OnItemClickListener listener;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(Season item);
    }

    public SeasonAdapter(Context context, List<Season> seasons, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.seasons.addAll(seasons);
    }

    public void addSeasons(List<Season> seasons) {
        this.seasons.addAll(seasons);
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(holder.movieImageUrlBuilder.buildPosterUrl(seasons.get(position).posterPath))
                .placeholder(R.drawable.movie_placeholder)
                .into(holder.posterImageView);
        holder.posterImageView.setOnClickListener(v -> listener.onItemClick(seasons.get(position)));
        holder.titleTextView.setText(seasons.get(position).name);
        String episodes = context.getText(R.string.episodes) + ": "
                + seasons.get(position).episodeCount;
        holder.episodesTextView.setText(episodes);
        if (seasons.get(position).airDate != null)
            holder.dateTextView.setText(seasons.get(position).airDate.split("-")[0]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

        private final ImageView posterImageView;
        private final TextView titleTextView, episodesTextView, dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            episodesTextView = itemView.findViewById(R.id.episodesTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
