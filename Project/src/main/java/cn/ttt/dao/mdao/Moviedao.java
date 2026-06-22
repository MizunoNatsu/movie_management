package cn.ttt.dao.mdao;
import cn.ttt.util.DBUtils;
import cn.ttt.entity.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Moviedao {

    // 1. 分页查询全部电影（首页分页使用）
    public List<Movie> getMovieByPage(int start, int pageSize) {
        List<Movie> list = new ArrayList<>();
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from movie limit ?,?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setMovieName(rs.getString("movie_name"));
                m.setType(rs.getString("type"));
                m.setYear(rs.getInt("year"));
                m.setScore(rs.getDouble("score"));
                m.setIntro(rs.getString("intro"));
                m.setClickCount(rs.getInt("click_count"));
                m.setWatchCount(rs.getInt("watch_count"));
                m.setPlayUrl(rs.getString("play_url"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt, rs);
        }
        return list;
    }

    // 2. 查询电影总条数，用于计算分页总页数
    public int getMovieCount() {
        Connection conn = DBUtils.getConn();
        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select count(*) from movie";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt, rs);
        }
        return count;
    }

    // 3. 热度榜单分页查询（热度=点击*1 + 观看*3 降序）
    public List<Movie> getHotRank(int start, int pageSize) {
        List<Movie> list = new ArrayList<>();
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select *, (click_count * 1 + watch_count * 3) as hot from movie order by hot desc limit ?,?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setMovieName(rs.getString("movie_name"));
                m.setType(rs.getString("type"));
                m.setYear(rs.getInt("year"));
                m.setScore(rs.getDouble("score"));
                m.setIntro(rs.getString("intro"));
                m.setClickCount(rs.getInt("click_count"));
                m.setWatchCount(rs.getInt("watch_count"));
                m.setHot(rs.getInt("hot"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt, rs);
        }
        return list;
    }

    // 4. 电影点击次数+1（点击电影时调用）
    public void addClick(int movieId) {
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        try {
            String sql = "update movie set click_count = click_count + 1 where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt);
        }
    }

    // 5. 完整观看次数+1（看完电影提交时调用）
    public void addWatch(int movieId) {
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        try {
            String sql = "update movie set watch_count = watch_count + 1 where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt);
        }
    }

    // 6. 批量插入Python爬虫抓取的电影（同步数据按钮使用）
    public int batchAddMovie(List<Movie> movieList) {
        Connection conn = DBUtils.getConn();
        int addNum = 0;
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO movie(movie_name,type,year,score,intro,click_count,watch_count,play_url) VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            for (Movie m : movieList) {
                // 判断：数据库没有这部电影才加入批处理
                if (!existMovieByName(m.getMovieName())) {
                    pstmt.setString(1, m.getMovieName());
                    pstmt.setString(2, m.getType());
                    pstmt.setInt(3, m.getYear());
                    pstmt.setDouble(4, m.getScore());
                    pstmt.setString(5, m.getIntro());
                    pstmt.setInt(6, m.getClickCount());
                    pstmt.setInt(7, m.getWatchCount());
                    pstmt.addBatch();
                    pstmt.setString(8, m.getPlayUrl());
                    addNum++;
                }
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt);
        }
        return addNum;
    }

    public Movie getMovieById(int id){
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Movie movie = null;
        try{
            String sql = "select * from movie where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setMovieName(rs.getString("movie_name"));
                movie.setType(rs.getString("type"));
                movie.setYear(rs.getInt("year"));
                movie.setScore(rs.getDouble("score"));
                movie.setIntro(rs.getString("intro"));
                movie.setClickCount(rs.getInt("click_count"));
                movie.setWatchCount(rs.getInt("watch_count"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.close(conn,pstmt,rs);
        }
        return movie;
    }

    public void addClickCount(int movieId){
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        try{
            String sql = "update movie set click_count = click_count + 1 where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,movieId);
            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.close(conn,pstmt,null);
        }
    }

    // 根据电影名称判断影片是否已存在
    public boolean existMovieByName(String movieName) {
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            String sql = "SELECT id FROM movie WHERE movie_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieName);
            rs = pstmt.executeQuery();
            // 查询到数据代表已存在
            if (rs.next()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt, rs);
        }
        return flag;
    }


    public Movie selectById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Movie movie = null;
        try {
            // 获取数据库连接（你自己的DBUtils工具）
            conn = DBUtils.getConn();
            String sql = "SELECT id,movie_name,type,year,score,intro,click_count,watch_count,play_url FROM movie WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setMovieName(rs.getString("movie_name"));
                movie.setType(rs.getString("type"));
                movie.setYear(Integer.valueOf(rs.getString("year")));
                movie.setScore(rs.getDouble("score"));
                movie.setIntro(rs.getString("intro"));
                movie.setClickCount(rs.getInt("click_count"));
                movie.setWatchCount(rs.getInt("watch_count"));
                // 读取新增的B站播放链接
                movie.setPlayUrl(rs.getString("play_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DBUtils.close(conn,pstmt,rs);
        }
        return movie;
    }

    public int update(Movie movie) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            conn = DBUtils.getConn();
            // 字段包含play_url，8个更新字段 + where id，一共9个?
            String sql = "UPDATE movie SET movie_name=?,type=?,year=?,score=?,intro=?,click_count=?,watch_count=?,play_url=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getMovieName());
            pstmt.setString(2, movie.getType());
            pstmt.setInt(3, movie.getYear());
            pstmt.setDouble(4, movie.getScore());
            pstmt.setString(5, movie.getIntro());
            pstmt.setInt(6, movie.getClickCount());
            pstmt.setInt(7, movie.getWatchCount());
            pstmt.setString(8, movie.getPlayUrl());
            pstmt.setInt(9, movie.getId());
            rows = pstmt.executeUpdate();
            System.out.println("更新影响行数："+rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, pstmt, null);
        }
        return rows;
    }
}