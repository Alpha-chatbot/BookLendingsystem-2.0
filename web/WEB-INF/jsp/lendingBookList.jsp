<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>预约图书列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>您已预约图书列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>预定学号</th>
                    <th>预约时间</th>
                    <th>图书ID</th>
                    <th>图书名称</th>
                    <th>图书简介</th>
                    <th>预约数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${lendingList}" var="sk">
                    <tr>
                        <td>${sk.studentId}</td>
                        <td>${sk.lendingTime}</td>
                        <td>${sk.bookId}</td>
                        <td>${sk.books.getBookName()}</td>
                        <td>${sk.books.getDetail()}</td><!--实际上对应Books类的方法-->
                        <td>1</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-md-4 column">

            <a class="btn btn-primary" href="${pageContext.request.contextPath}/books/list">返回图书列表</a>
        </div>
    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<!--  Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>