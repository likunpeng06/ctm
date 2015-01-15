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
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/main.css">
    <script src="${ctx}/resources/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap.min.js"></script>
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
        用户修改
        <%--<c:choose>--%>
        <%--<c:when test="${userForm.id == null}">--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--用户修改--%>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    </li>
</ol>
<%--<div class="login-single-panel-header">--%>
    <%--<h5 style="color:red">${errorMessage}</h5>--%>
<%--</div>--%>
<div class="">
    <legend class=""></legend>
</div>
    <div class="container">
            <form:form class="form-horizontal" action="${ctx}/user/update" method="post" commandName="userForm">
                <input type="hidden" name="user.id" value="${userForm.user.id}"/>
                <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                <div class="form-group">
                    <label class="col-md-3 control-label">用户名</label>
                    <div class="col-md-6">
                        <p class="form-control-static">${userForm.user.username}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">邮箱</label>
                    <div class="col-md-6">
                        <p class="form-control-static">${userForm.user.email}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">手机号</label>
                    <div class="col-md-6">
                        <input type="text" class="form-control" name="user.mobile" value="${userForm.user.mobile}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="col-md-3 control-label" style="width:23%;">是否有效</label>
                    <div class="form-group">
                        <label class="radio-inline">
                        <form:select cssClass="form-control" path="user.valid" items="${enableDisableStatusList}" itemValue="value" itemLabel="name"/>
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