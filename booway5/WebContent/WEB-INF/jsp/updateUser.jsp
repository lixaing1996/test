<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JDBC修改用户界面</title>
</head>
<body>
<form action = "/booway5/userUpdate" method="get">
姓名：<input type="text" name="username">
年龄：<input type="text" name="age">
<input type="hidden" name="id" value="${userId}">
<input type="hidden" name="flag" value="update">
<input type="submit">
</form>
</body>

</html>