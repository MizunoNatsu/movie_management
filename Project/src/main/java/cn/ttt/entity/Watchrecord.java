package cn.ttt.entity;

import java.util.Date;

public class Watchrecord {
    private Integer id;
    private Integer userId;
    private Integer movieId;
    private Date watchTime;

    public Watchrecord(){}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getMovieId() {
        return movieId;
    }
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Date getWatchTime() {
        return watchTime;
    }
    public void setWatchTime(Date watchTime) {
        this.watchTime = watchTime;
    }
}