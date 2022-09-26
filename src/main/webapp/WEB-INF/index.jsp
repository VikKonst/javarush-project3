<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>
<body>
<div align="center" class="fst-italic">
    <img  src="${pageContext.request.contextPath}/assets/detective_logo.png" class="img-fluid" alt="logo" style="height:413px">
    <h1 style="font-family: fantasy Papyrus">Добро пожаловать на квест!</h1>
    <h2 style="font-family: fantasy Papyrus">Представьтесь, пожалуйста:</h2>
    <form action="${pageContext.request.contextPath}/entrance" method="post">
        <div>
            <label for="name"></label>
            <input type="text" id="name" name="username">
        </div>
        <div class="button" style="margin:10px">
            <button type="submit" class="btn btn-dark">Начать</button>
        </div>
    </form>
</div>
</body>
</html>
