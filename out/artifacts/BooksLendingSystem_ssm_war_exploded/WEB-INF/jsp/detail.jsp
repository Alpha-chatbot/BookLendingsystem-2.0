<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>预约详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>图书详情</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>图书ID</th>
                    <th>图书名称</th>
                    <th>图书简介</th>
                    <th>剩余数量</th>
                    <th>预约数量</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${books.bookId}</td>
                    <td>${books.bookName}</td>
                    <td>${books.detail}</td>
                    <td>${books.number}</td>
                    <td>1</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="panel-body text-center">
            <h2 class="text-danger">
                <!--用来展示预约等控件-->
                <span class="glyphicon" id="lending-box"></span> <!--在js里面调用这个id还可以动态显示一些其他东西，例如动态时间等（需要插件）-->

                <span class="glyphicon"><a class="btn btn-primary btn-lg" href="/books/lending?studentId=${cookie['studentId'].value}" target="_blank">查看我已预约的书籍</a></span>

                <span class="glyphicon" id="returning-box"></span>

            </h2>           <!--如何获取该页面弹出层输入的学生ID， 传给上面的url-->
        </div>
        <div class="col-md-4 column">

            <a class="btn btn-primary" href="${pageContext.request.contextPath}/books/list">返回图书列表</a>
        </div>


    </div>

</div>
<!--  登录弹出层 输入对话   -->
<div id="varifyModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-studentId"> </span>请输入学号和密码:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="studentId" id="studentIdKey"
                               placeholder="填写学号" class="form-control">
                    </div>
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="password" name="password" id="passwordKey"
                               placeholder="输入密码" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <!--  验证信息 -->
                <span id="studentMessage" class="glyphicon"> </span>
                <button type="button" id="studentBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-student"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>

</body>
<%--jQery文件,务必在bootstrap.min.js之前引入--%>
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<%--使用CDN 获取公共js http://www.bootcdn.cn/--%>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>


<script src="${pageContext.request.contextPath}/script/booklending.js"></script>


<script type="text/javascript">
    $(function () {
        //使用EL表达式传入参数
        booklending.detail.init({
            bookId:${books.bookId}

        });
    })
</script>
</html>