<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欢迎</title>
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="keywords" content="" />
    <meta http-equiv="description" content="" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/main.css">
    <script src="${ctx}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
</head>
<body>
<%--<jsp:include page="/WEB-INF/jsp/navi.jsp"/>--%>
<div class="container-fluid">
<div class="col-lg-2">
    <%--<jsp:include page="/WEB-INF/jsp/menu.jsp"/>--%>
</div>
<div class="col-lg-10">
<ol class="breadcrumb">
    <li><a href="#">系统管理</a></li>
    <li><a href="${ctx}/user/list">用户管理</a></li>
    <li class="active">
        用户添加
        <%--<c:choose>--%>
        <%--<c:when test="${userForm.id == null}">--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--用户修改--%>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    </li>
</ol>
<div class="login-single-panel-header">
    <h5 style="color:red">${errorMessage}</h5>
</div>
<div class="">
    <legend class=""></legend>
</div>
    <div class="container">
            <form:form class="form-horizontal" action="${ctx}/user/create" method="post" commandName="user">
                <div class="form-group">
                    <label class="col-lg-3 control-label">用户名</label>
                    <div class="col-lg-6">
                        <input type="text" class="form-control" name="username" value="${user.username}" autofocus="" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">密码</label>
                    <div class="col-lg-6">
                        <input type="password" class="form-control" name="password" value="${user.password}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">确认密码</label>
                    <div class="col-lg-6">
                        <input type="password" class="form-control" name="conPassword" value="${user.conPassword}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">邮箱</label>
                    <div class="col-lg-6">
                        <input type="text" class="form-control" name="email" value="${user.email}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">手机号</label>
                    <div class="col-lg-6">
                        <input type="text" class="form-control" name="mobile" value="${user.mobile}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="col-lg-3 control-label" style="width:23%;">是否有效</label>
                    <div class="form-group">
                        <label class="radio-inline">
                        <form:select cssClass="form-control" path="valid" items="${enableDisableStatusList}" itemValue="value" itemLabel="name"/>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </form:form>
    </div>
    </div>
</div>
</body>
</html>