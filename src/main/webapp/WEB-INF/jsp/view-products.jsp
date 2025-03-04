<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Books</title>
    <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>Name</th>
        <th>Proteins</th>
        <th>Fats</th>
        <th>Carbohydrates</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${products}" var="product">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.proteins}</td>
            <td>${product.fats}</td>
            <td>${product.carbohydrates}</td>
            <td>${product.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
