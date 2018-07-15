package com.apolotech.seriesmanager.Model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.squareup.moshi.Json;

import java.util.Collection;
import java.util.List;

@DatabaseTable
public class Movie {

    @DatabaseField(id=true)
    public int id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String overview;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @Json(name = "genres")
    public List<Genre> genres;

    @DatabaseField
    @Json(name = "poster_path")
    public String posterPath;

    @DatabaseField
    @Json(name = "backdrop_path")
    public String backdropPath;

    @DatabaseField
    @Json(name = "first_air_date")
    public String releaseDate;

    @Json(name = "seasons")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public List<Season> seasons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (name != null ? !name.equals(movie.name) : movie.name != null) return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (genres != null ? !genres.equals(movie.genres) : movie.genres != null)
            return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null)
            return false;
        if (backdropPath != null ? !backdropPath.equals(movie.backdropPath) : movie.backdropPath != null)
            return false;
        if (seasons != null ? !seasons.equals(movie.seasons) : movie.seasons != null)
            return false;
        return releaseDate != null ? releaseDate.equals(movie.releaseDate) : movie.releaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (seasons != null ? seasons.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", seasons='" + seasons + '\'' +
                '}';
    }
}
