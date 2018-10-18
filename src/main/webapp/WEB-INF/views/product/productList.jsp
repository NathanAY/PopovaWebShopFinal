<%@ page contentType = "text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/resources/css/product.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>


<div class="sidenav">
    <a href="?category=All">All</a>
    <c:forEach items="${categories}" var="category">
        <a href="?category=${category.name}">${category.name}</a>
    </c:forEach>
</div>

<div class="main">

    <div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="basket?search=${pageContext.request.userPrincipal.name}">Basket</a>
        <a href="/orders">Orders</a>
        <a href="/categories">Categories</a>
        <a href="/addProduct">New Product</a>
        <a href="/deleteProduct">Delete Product</a>
        <a href="/userList">Users</a>
        <a href="/account">Account</a>
        <a href="/login?logout">Log out</a>
        <div class="search-container">
            <form action="/search">
                <input type="text" placeholder="Search.." name="search">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>
    </div>

    <h2>Products</h2>
    <%--<p>This sidenav is always shown.</p>--%>
    <%--<p>1 ${cookie.keySet()} ${cookie.entrySet()}</p>--%>
    <%--<p>2 ${sessionScope.values()}</p>--%>
    <%--<p>2 ${session.toString()}</p>--%>
    <%--<p>3 ${sessionScope.get("SPRING_SECURITY_CONTEXT").toString()}</p>--%>
<%----%>
<%----%>
    <%--<p>4 ${pageContext.request}</p>--%>
    <%--<p>5 ${pageContext.getRequest().toString()}</p>--%>
    <%--<p>6 ${pageContext.getPage().toString()}</p>--%>
    <%--<p>7 ${pageContext.getOut().toString()}</p>--%>
    <%--<p>8 ${pageContext.getRequest().userPrincipal}</p>--%>
<%----%>
    <%--<p>9 ${pageContext.getSession().toString()}</p>--%>

    <div class="container">

            <c:forEach items="${list}" var="item">
            <div class="col-xs-12 col-md-6">
                <!-- First product box start here-->
                <div class="prod-info-main prod-wrap clearfix">
                    <div class="row">
                        <div class="col-md-5 col-sm-12 col-xs-12">
                            <div class="product-image">
                                <img src="${contextPath}/resources/images/products/p4.png" class="img-responsive">
                                <span class="tag2 hot">
							HOT
						</span>
                            </div>
                        </div>
                        <div class="col-md-7 col-sm-12 col-xs-12">
                            <div class="product-deatil">
                                <h5 class="name">
                                    <a href="#">
                                        <c:out value="${item.name}"/>
                                    </a>
                                    <a href="#">
                                        <span>Id 140${item.id}</span>
                                    </a>

                                </h5>
                                <p class="price-container">
                                    <span>${item.price}</span>
                                </p>
                                <span class="tag1"></span>
                            </div>
                            <div class="description">
                                <p>${item.description}</p>
                            </div>
                            <div class="product-info smart-form">
                                <div class="row">
                                    <div class="col-md-12">
                                        <a onclick="addToBasket(${item.id}, '${item.name}')" class="btn btn-danger">Add to basket</a>
                                        <a href="product/${item.id}" class="btn btn-info">More info</a>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="rating">Rating:
                                            <label for="stars-rating-5"><i class="fa fa-star text-danger"></i></label>
                                            <label for="stars-rating-4"><i class="fa fa-star text-danger"></i></label>
                                            <label for="stars-rating-3"><i class="fa fa-star text-danger"></i></label>
                                            <label for="stars-rating-2"><i class="fa fa-star text-warning"></i></label>
                                            <label for="stars-rating-1"><i class="fa fa-star text-warning"></i></label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>

</div>
<script>
    function addToBasket(productId, productName) {
        $.post("addToBasket?${_csrf.parameterName}=${_csrf.token}",
            {
                name: "${pageContext.request.userPrincipal.name}",
                product: productId
            },
            function(data){
                alert("Data: " + data); //TODO всплывающее окно
            });
        alert("'" + productName + "' added to your basket.");
    }
</script>


</body>
</html>