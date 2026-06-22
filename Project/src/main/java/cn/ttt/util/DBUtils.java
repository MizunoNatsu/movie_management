package cn.ttt.util;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBUtils {
    public static Connection getConn(){
        Connection conn = null;
        try {
            InputStream is = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties pro = new Properties();
            pro.load(is);
            String driver = pro.getProperty("driver");
            String url = pro.getProperty("url");
            String user = pro.getProperty("user");
            String pwd = pro.getProperty("password");
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void close(Connection conn, PreparedStatement pstmt){
        close(conn, pstmt, null);
    }
}