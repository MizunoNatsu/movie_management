<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${movie.movieName} - 电影详情</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<h1>${movie.movieName}</h1>
<a href="index">← 返回全部电影列表</a>
<hr>
<p>影片类型：${movie.type}</p>
<p>上映年份：${movie.year}</p>
<p>豆瓣评分：${movie.score}</p>
<p>影片简介：${movie.intro}</p>
<p>累计点击：${movie.clickCount}</p>
<p>完整观看次数：${movie.watchCount}</p>
<p>实时热度值：${movie.clickCount * 1 + movie.watchCount * 3}</p>
<hr>
<h3>编辑电影信息</h3>
<form action="detail" method="post">
    <input type="hidden" name="id" value="${movie.id}">
    <input type="hidden" name="clickCount" value="${movie.clickCount}">
    <input type="hidden" name="watchCount" value="${movie.watchCount}">
    <p>电影名称：<input type="text" name="movieName" value="${movie.movieName}"></p>
    <p>影片类型：<input type="text" name="type" value="${movie.type}"></p>
    <p>上映年份：<input type="text" name="year" value="${movie.year}"></p>
    <p>豆瓣评分：<input type="text" name="score" value="${movie.score}"></p>
    <p>影片简介：<textarea name="intro" rows="3" cols="40">${movie.intro}</textarea></p>
    <p>B站嵌入播放链接：
        <input type="text" name="playUrl" value="${empty movie.playUrl ?"":movie.playUrl}" style="width:600px;">
    </p>
    <button type="submit">保存修改</button>
</form>
</body>
</html>