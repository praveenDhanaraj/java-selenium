<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<h1>This is a ZippyOPS Demo Application<h1>
<img alt="" src="zippy.png">
<h4>

This is a Java App deployed by  Kubernetes on  <%out.println(System.getProperty("os.name"));%> at <%= (new java.util.Date()).toLocaleString()%> for validation***.
</h4>
</center>
</body>
</html>