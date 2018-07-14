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

    @GET("search/movie")
    Observable<MoviesResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Long page,
            @Query("region") String region,
            @Query("query") String query
    );

    @GET("movie/{id}")
    Observable<Movie> movie(
            @Path("id") Long id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
