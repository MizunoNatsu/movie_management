<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>电影热度榜单</title>
    <link rel="stylesheet" href="css/hot.css">
</head>
<body>
<h1>电影热度排行榜</h1>
<div class="nav-bar">
    <span>欢迎：${loginUser.realName}</span>
    <a href="index">← 返回全部电影列表</a>
</div>
<p class="tip-text">热度计算规则：热度 = 点击次数 × 1 + 完整观看次数 × 3</p>

<table>
    <tr>
        <th width="6%">排名</th>
        <th width="20%">电影名称</th>
        <th width="13%">点击次数</th>
        <th width="13%">观看次数</th>
        <th width="10%">热度值</th>
        <th width="38%">简介</th>
    </tr>
    <c:forEach items="${hotList}" var="movie" varStatus="stat">
        <tr>
            <td>${(pageNum - 1)*10 + stat.count}</td>
            <td>${movie.movieName}</td>
            <td>${movie.clickCount}</td>
            <td>${movie.watchCount}</td>
            <td class="hot-num">${movie.hot}</td>
            <td>${movie.intro}</td>
        </tr>
    </c:forEach>
</table>

<div class="page-wrapper">
    <c:if test="${pageNum > 1}">
        <a href="hotRank?pageNum=${pageNum - 1}">上一页</a>
    </c:if>
    <c:if test="${pageNum == 1}">
        <span class="disable-page">上一页</span>
    </c:if>

    <c:forEach begin="1" end="${totalPage}" var="i">
        <c:if test="${i == pageNum}">
            <span class="current-page">${i}</span>
        </c:if>
        <c:if test="${i != pageNum}">
            <a href="hotRank?pageNum=${i}">${i}</a>
        </c:if>
    </c:forEach>

    <c:if test="${pageNum < totalPage}">
        <a href="hotRank?pageNum=${pageNum + 1}">下一页</a>
    </c:if>
    <c:if test="${pageNum == totalPage}">
        <span class="disable-page">下一页</span>
    </c:if>
</div>
</body>
</html>