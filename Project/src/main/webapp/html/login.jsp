<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>电影后台管理系统</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<h1>电影后台管理系统</h1>
<c:if test="${msg != null}">
    <div class="error-msg">${msg}</div>
</c:if>
<form action="login" method="post">
    <div class="form-item">
        <label>管理员账号</label>
        <input type="text" name="username" placeholder="请输入账号">
    </div>
    <div class="form-item">
        <label>登录密码</label>
        <input type="password" name="password" placeholder="请输入密码">
    </div>
    <div class="form-item">
        <label></label>
        <button class="submit-btn" type="submit">登录系统</button>
    </div>
</form>
<div class="jump-link">
    <a href="index">访问电影首页</a>
</div>
</body>
</html>