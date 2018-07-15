package com.apolotech.seriesmanager.Rest;


import com.apolotech.seriesmanager.Model.GenreResponse;
import com.apolotech.seriesmanager.Model.Movie;
import com.apolotech.seriesmanager.Model.MoviesResponse;
import com.apolotech.seriesmanager.Model.UpcomingMoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    String URL = "https://api.themoviedb.org/3/";
    String API_KEY = "346705b46d2e0a7cae656ba9577a143c";
    String DEFAULT_LANGUAGE = "pt-BR";
    String DEFAULT_REGION = "BR";

    @GET("genre/movie/list")
    Observable<GenreResponse> genres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Observable<UpcomingMoviesResponse> upcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Long page,
            @Query("region") String region
    );

    @GET("search/tv")
    Observable<MoviesResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Long page,
            @Query("region") String region,
            @Query("query") String query
    );

    @GET("tv/{id}")
    Observable<Movie> getMovie(
            @Path("id") Integer id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("region") String region
    );

    @GET("tv/{serie_id}/season/{season_id}")
    Observable<Movie> getSeason(
            @Path("serie_id") Integer serie_id,
            @Path("season_id") Integer season_id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("region") String region
    );
}
