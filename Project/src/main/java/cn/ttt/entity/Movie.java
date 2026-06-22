package cn.ttt.entity;

public class Movie {
    private Integer id;
    private String movieName;
    private String type;
    private Integer year;
    private Double score;
    private String intro;
    // 新增点击、观看、热度字段
    private Integer clickCount;
    private Integer watchCount;
    private Integer hot;
    private String playurl;
    
    // 无参构造
    public Movie(){}

    // get/set
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public String getIntro() {
        return intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getClickCount() {
        return clickCount;
    }
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Integer getWatchCount() {
        return watchCount;
    }
    public void setWatchCount(Integer watchCount) {
        this.watchCount = watchCount;
    }

    public Integer getHot() {
        return hot;
    }
    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getPlayUrl(){
        return playurl;
    }
    public void setPlayUrl(String playurl){
        this.playurl = playurl;
    }


}