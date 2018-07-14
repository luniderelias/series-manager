package com.apolotech.seriesmanager.Service;

import com.apolotech.seriesmanager.Model.GenreResponse;
import com.apolotech.seriesmanager.Model.MoviesResponse;
import com.apolotech.seriesmanager.Rest.TmdbApi;
import com.apolotech.seriesmanager.Util.RestUtil;

import org.androidannotations.annotations.EBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@EBean
public class MovieService {

    public synchronized Observable<MoviesResponse> getMovies(Long page, String query)  {
        return RestUtil.api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public synchronized Observable<GenreResponse> getGenres(){
        return RestUtil.api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
