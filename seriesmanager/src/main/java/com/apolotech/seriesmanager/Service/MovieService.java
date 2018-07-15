package com.apolotech.seriesmanager.Service;

import com.apolotech.seriesmanager.Model.GenreResponse;
import com.apolotech.seriesmanager.Model.Movie;
import com.apolotech.seriesmanager.Model.MoviesResponse;
import com.apolotech.seriesmanager.Rest.TmdbApi;
import com.apolotech.seriesmanager.Util.DatabaseHelper;
import com.apolotech.seriesmanager.Util.RestUtil;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.observable.ObservableAny;
import io.reactivex.schedulers.Schedulers;

@EBean
public class MovieService {

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Movie, Integer> movieDao;

    public synchronized Observable<MoviesResponse> getMovies(Long page, String query)  {
        return RestUtil.api.searchMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public synchronized Observable<Movie> getMovie(Integer id)  {
        return RestUtil.api.getMovie(id, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public synchronized Observable<GenreResponse> getGenres(){
        return RestUtil.api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<Movie> getLocalMovies() throws SQLException{
        return movieDao.queryForAll();
    }
}
