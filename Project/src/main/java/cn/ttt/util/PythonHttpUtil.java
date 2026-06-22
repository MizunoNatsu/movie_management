package cn.ttt.util;

import cn.ttt.entity.Movie;
import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PythonHttpUtil {
    // 调用Python爬虫接口，返回电影集合
    public static List<Movie> getCrawlMovieData() {
        List<Movie> movieList = null;
        String pythonApiUrl = "http://127.0.0.1:5000/movie";
        BufferedReader br = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(pythonApiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 读取接口返回的JSON字符串
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            // fastjson把JSON数组转List<Movie>
            String jsonStr = sb.toString();
            movieList = JSON.parseArray(jsonStr, Movie.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流和连接
            try {
                if (br != null) br.close();
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return movieList;
    }
}