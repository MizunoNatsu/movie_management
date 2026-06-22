<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>电影数据管理平台</title>
  <link rel="stylesheet" href="css/index.css">
</head>
<body>
<h1>电影数据管理平台</h1>
<c:if test="${msg != null}">
  <p style="color:green">${msg}</p>
</c:if>
<div class="nav-bar">
  <span>欢迎用户：${loginUser.realName}</span>
  <a href="hotRank">查看热度排行榜</a>
</div>

<h2>全部电影列表</h2>
<table>
  <tr>
    <th width="6%">序号</th>
    <th width="22%">电影名称</th>
    <th width="13%">类型</th>
    <th width="12%">上映年份</th>
    <th width="10%">评分</th>
    <th width="37%">电影简介</th>
    <th width="12%">play操作</th>
  </tr>
  <c:forEach items="${movieList}" var="movie" varStatus="stat">
    <tr>
      <td>${(pageNum - 1)*10 + stat.count}</td>
      <td>
        <a href="detail?id=${movie.id}">${movie.movieName}</a>
      </td>
      <td>${movie.type}</td>
      <td>${movie.year}</td>
      <td>${movie.score}</td>
      <td>${movie.intro}</td>
      <td>
        <c:if test="${empty loginUser}">
          <button disabled onclick="alert('请先登录后才可观看影片')">播放</button>
        </c:if>
        <c:if test="${not empty loginUser}">
          <c:if test="${empty movie.playUrl}">
            <button disabled>暂无播放资源</button>
          </c:if>
          <c:if test="${not empty movie.playUrl}">
            <a href="PlayServlet?id=${movie.id}">
              <button>立即播放</button>
            </a>
          </c:if>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>

<div class="page-wrapper">
  <c:if test="${pageNum > 1}">
    <a href="index?pageNum=${pageNum - 1}">上一页</a>
  </c:if>
  <c:if test="${pageNum == 1}">
    <span class="disable-page">上一页</span>
  </c:if>

  <c:forEach begin="1" end="${totalPage}" var="i">
    <c:if test="${i == pageNum}">
      <span class="current-page">${i}</span>
    </c:if>
    <c:if test="${i != pageNum}">
      <a href="index?pageNum=${i}">${i}</a>
    </c:if>
  </c:forEach>

  <c:if test="${pageNum < totalPage}">
    <a href="index?pageNum=${pageNum + 1}">下一页</a>
  </c:if>
  <c:if test="${pageNum == totalPage}">
    <span class="disable-page">下一页</span>
  </c:if>
</div>
</body>
</html>