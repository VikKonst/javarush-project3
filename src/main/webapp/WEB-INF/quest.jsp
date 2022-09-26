<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Квест</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quest.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<div class="masthead" style="background-image: url('${pageContext.request.contextPath}/assets/background2.png');">
    <div class="color-overlay d-flex flex-column justify-content-center align-items-center">
        <h1 class="fw-bolder">История Льюиса Молтби</h1>
        <p class="description" style="font-family: Gill Sans, sans-serif">
             ${user.getCurrentQuestStep().getDescription()}
        </p>
        <form action="${pageContext.request.contextPath}/quest" method="post">
            <c:forEach items="${user.getCurrentQuestStep().getActions()}" var="action">
                <input id="${action.getGoTo()}" type="radio" name="nextStep" value="${action.getGoTo()}">
                <label for="${action.getGoTo()}">${action.getTitle()}</label><br>
            </c:forEach>
            <br><button type="submit" class="btn btn-dark" style="margin:10px">Выбрать</button>
        </form>
    </div>
</div>
</body>
</html>