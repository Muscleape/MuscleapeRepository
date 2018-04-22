<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path =  request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
  <base href="<%=basePath%>">
  <title>My JSP 'index.jsp' starting page</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keywords1,keywords2,keywords3">
  <meta http-equiv="description" content="This is my page">
  <!-- 
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
</head>

<body>
This is my JSP page.<br>
</body>

</html>
