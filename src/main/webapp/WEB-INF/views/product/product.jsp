<%@ page contentType = "text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/resources/css/product.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
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
        <a class="active" href="#home">Home</a>
        <a href="#about">About</a>
        <a href="#contact">Contact</a>
        <div class="search-container">
            <form action="/userList.php">
                <input type="text" placeholder="Search.." name="search">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>
    </div>

    <h2>Sidenav Example</h2>
    <p>This sidenav is always shown.</p>

        <div class="container">

            <c:forEach items="${list}" var="item">
            <div class="col-xs-12 col-md-6">
                <!-- First product box start here-->
                <div class="prod-info-main prod-wrap clearfix">
                    <div class="row">
                        <div class="col-md-5 col-sm-12 col-xs-12">
                            <div class="product-image">
                                <img src="images/products/p4.png" class="img-responsive">
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
                                        <span>Product Category</span>
                                    </a>

                                </h5>
                                <p class="price-container">
                                    <span>$199</span>
                                </p>
                                <span class="tag1"></span>
                            </div>
                            <div class="description">
                                <p>A Short product description here </p>
                            </div>
                            <div class="product-info smart-form">
                                <div class="row">
                                    <div class="col-md-12">
                                        <a href="javascript:void(0);" class="btn btn-danger">Add to cart</a>
                                        <a href="javascript:void(0);" class="btn btn-info">More info</a>
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
                <!-- end product -->
            </div>
            </c:forEach>

        </div>

    <form:form method="POST" modelAttribute="productForm" class="form-signin">
        <h2 class="form-signin-heading">Change your product</h2>

        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control" placeholder="Name"
                            autofocus="true"></form:input>
                <form:errors path="name"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="description" class="form-control" placeholder="Description"
                            autofocus="true"></form:input>
                <form:errors path="description"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="categoryName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="categoryName" class="form-control" placeholder="Category"
                            autofocus="true"></form:input>
                <form:errors path="categoryName"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="price">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="price" class="form-control" placeholder="Price"
                            autofocus="true"></form:input>
                <form:errors path="price"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Edit</button>
    </form:form>

</div>

</div>


</body>
</html>