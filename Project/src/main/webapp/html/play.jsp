<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${movie.movieName}</title>
    <style>
        .video-box{width:1100px;margin:40px auto;}
        iframe{width:100%;height:620px;border:0;}
    </style>
</head>
<body>
<div class="video-box">
    <h2>${movie.movieName}</h2>
    <iframe src="${movie.playUrl}" allowfullscreen></iframe>
    <br><br>
    <a href="IndexServlet">← 返回电影列表</a>
</div>
</body>
</html>