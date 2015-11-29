<%--
  Created by IntelliJ IDEA.
  User: YeahO_O
  Date: 11/26/2015
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Hello [${user.username}]${user.remark}<br>
Hello [${userSecure.username}]${userSecure.remark}, pwd=${userSecure.password} <br>
</body>
</html>
