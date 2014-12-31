<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>用户列表</title>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="keywords" content="" />
        <meta http-equiv="description" content="" />
        <link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx}/resources/css/main.css">
        <script src="${ctx}/resources/js/jquery-1.11.1.min.js"></script>
        <script src="${ctx}/resources/bootstrap.min.js"></script>
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
                    <li class="active">用户列表</li>
                </ol>
                <div class="container-fluid">
                    <a href="${ctx}/user/create">新建用户</a>
                </div>
                <div class="container-fluid">
                    <div class="">
                        <legend class=""><h4>用户列表</h4></legend>
                    </div>
                    <c:if test="${userList != null}">
                        <div class="text-center">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>编号</th>
                                        <th>用户名</th>
                                        <th>邮箱</th>
                                        <th>手机号</th>
                                        <th>创建时间</th>
                                        <th>更新时间</th>
                                        <th>是否有效</th>
                                        <th>邮箱是否已验证</th>
                                        <th>手机是否已验证</th>
                                        <th colspan="3">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${userList}" var="user" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td>${user.id}</td>
                                            <td><a href="${ctx}/user/view/${user.id}">${user.username}</a></td>
                                            <td>${user.email}</td>
                                            <td>${user.mobile}</td>
                                            <td><fmt:formatDate value="${user.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                            <td><fmt:formatDate value="${user.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                            <td>${user.valid.name}</td>
                                            <td>${user.emailValid.name}</td>
                                            <td>${user.mobileValid.name}</td>
                                            <td><a href="${ctx}/user/update/${user.id}">修改</a></td>
                                            <td><a href="${ctx}/user/password/input/${user.id}">重置密码</a></td>
                                            <td><a href="${ctx}/user/delete/${user.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>