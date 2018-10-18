<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/resources/css/product.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <script src="${contextPath}/resources/js/jquery-3.3.1.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
          integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
          crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<div class="sidenav">
    <a href="#">About</a>
    <a href="#">Services</a>
    <a href="#">Clients</a>
    <a href="#">Contact</a>
</div>

<div class="main">
    <div class="topnav">
        <a class="active" href="/productList">Products</a>
        <a href="#about">About</a>
        <a href="#contact">Contact</a>
        <div class="search-container">
            <form action="/search">
                <input type="text" placeholder="Search.." name="search">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>
    </div>
    <div class="container">
        <h2>Condensed Table</h2>
        <p>The .table-condensed class makes a table more compact by cutting cell padding in half:</p>
        <table class="table table-condensed">
            <thead>
            <tr>
                <th>Id</th>
                <th>Category</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categories}" var="category">
                <tr id="${category.id}">
                    <td>${category.id}</td>
                    <td class="Category name">${category.name}</td>
                    <td>
                        <button type="button" class="btn btn-danger btn-sm" onclick="deleteCategory(${category.id})">X
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <h2>Create new category</h2>
    <form class="form-inline">
        <div class="form-group">
            <label for="exampleInputName2">Name</label>
            <input type="text" class="form-control" id="exampleInputName2" placeholder="New">
        </div>
        <button type="button" class="btn btn-success btn-lg" onclick="createCategory()">
            New Category
        </button>
    </form>
</div>
</div>

<script>
    function deleteCategory(orderId) {
        $.post("deleteCategory?${_csrf.parameterName}=${_csrf.token}",
            {
                name: "${pageContext.request.userPrincipal.name}",
                categoryId: orderId
            },
            function (data) {
            });
        $("#" + orderId).remove();
        alert("Category removed successfully.");
    }

    function createCategory() {
        var name = document.getElementById("exampleInputName2").value;
        var categories = document.getElementsByClassName("Category name");
        for(var i = 0; i < categories.length; i++){
            if (name == categories[i].innerText) {
                alert("This category already exist");
                return;
            }
        }
        if (name === '' || name.length > 32) {
            alert("Invalid category length.");
            return;
        } else {
            $.post("createCategory?${_csrf.parameterName}=${_csrf.token}",
                {
                    name: name
                },
                function (data) {
                });
            alert("Category created successfully.");
        }
    }
</script>

</body>
</html>