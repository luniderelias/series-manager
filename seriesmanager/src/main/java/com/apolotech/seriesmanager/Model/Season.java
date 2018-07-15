package com.apolotech.seriesmanager.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.squareup.moshi.Json;

@DatabaseTable
public class Season {

    @DatabaseField(id=true)
    public Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "movie")
    public Movie movie;

    @DatabaseField
    public String name;

    @DatabaseField()
    @Json(name = "episode_count")
    public Integer episodeCount;

    @DatabaseField()
    @Json(name = "air_date")
    public String airDate;

    @DatabaseField()
    @Json(name = "season_number")
    public Integer seasonNumber;

    @DatabaseField()
    @Json(name = "poster_path")
    public String posterPath;
}
