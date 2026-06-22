import requests
import pymysql
import time

# ===================== 配置区（改成你自己的数据库信息）=====================
DB_HOST = "localhost"
DB_USER = "root"
DB_PWD = "123456"
DB_NAME = "catch_movie"
# B站请求头，模拟浏览器
HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
}
# ==========================================================================

def get_db_conn():
    """获取数据库连接"""
    conn = pymysql.connect(
        host=DB_HOST,
        user=DB_USER,
        password=DB_PWD,
        database=DB_NAME,
        charset="utf8mb4"
    )
    return conn

def search_bilibili_video(movie_name):
    """根据电影名搜索B站，返回嵌入播放链接"""
    try:
        # B站搜索API
        url = f"https://api.bilibili.com/x/web-interface/search/all?keyword={movie_name}"
        resp = requests.get(url, headers=HEADERS, timeout=10)
        data = resp.json()
        # 取第一条视频bvid
        video_list = data["data"]["result"]["video"]
        if not video_list:
            print(f"【{movie_name}】未搜到B站视频")
            return None
        bvid = video_list[0]["bvid"]
        # 拼接iframe嵌入链接
        play_url = f"https://player.bilibili.com/player.html?bvid={bvid}"
        print(f"【{movie_name}】匹配成功：{play_url}")
        return play_url
    except Exception as e:
        print(f"【{movie_name}】搜索失败：{str(e)}")
        return None

def batch_fill_play_url():
    """批量给数据库空链接电影填充播放地址"""
    conn = get_db_conn()
    cur = conn.cursor()
    # 查询所有play_url为null的电影
    cur.execute("SELECT id, movie_name FROM movie WHERE play_url IS NULL")
    movie_list = cur.fetchall()

    if not movie_list:
        print("所有电影均已填充播放链接，无需处理")
        cur.close()
        conn.close()
        return

    print(f"待处理电影总数：{len(movie_list)}")
    # 循环匹配链接并更新数据库
    for mid, m_name in movie_list:
        link = search_bilibili_video(m_name)
        if link:
            update_sql = "UPDATE movie SET play_url=%s WHERE id=%s"
            cur.execute(update_sql, (link, mid))
            conn.commit()
        time.sleep(1)  # 延迟1秒，防止B站封IP

    cur.close()
    conn.close()
    print("全部处理完成！打开Navicat刷新movie表查看play_url字段")

if __name__ == "__main__":
    batch_fill_play_url()