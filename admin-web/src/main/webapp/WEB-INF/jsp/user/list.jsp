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
        <link rel="stylesheet" href="${ctx}/resources/css/datetimepicker/bootstrap-datetimepicker.min.css">
        <script src="${ctx}/resources/js/jquery-1.11.1.min.js"></script>
        <script src="${ctx}/resources/js/bootstrap.min.js"></script>
        <script src="${ctx}/resources/js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
        <script src="${ctx}/resources/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
        <script type="text/javascript">
            var goPage = function(page) {
                $("#page").val(page);
                $("#pageForm").submit();
            }
            $(function() {
                $(".form_datetime").datetimepicker({
                    format: "yyyy-mm-dd hh:ii:ss",
                    minuteStep: 1,
                    todayBtn:  1,
                    autoclose: 1
                });

            });
        </script>
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
                    <form class="form-inline" id="queryForm" action="${ctx}/user/list" method="post">
                        <div class="row wrapper">
                            <div class="col-sm-3 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon">用户编码：</span>
                                    <input type="text" name="searchable.id" id="userid" value="${userForm.searchable.id}" class="form-control">
                                </div>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon">用户名：</span>
                                    <input type="text" name="searchable.username" id="username" value="${userForm.searchable.username}" class="form-control">
                                </div>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon">邮箱：</span>
                                    <input type="text" name="searchable.email" id="email" value="${userForm.searchable.email}" class="form-control">
                                </div>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon">手机号：</span>
                                    <input type="text" name="searchable.mobile" id="mobile" value="${userForm.searchable.mobile}" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="row wrapper">
                            <div class="col-sm-12 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon">开始创建时间：</span>
                                    <div class="input-group date form_datetime">
                                        <input class="form-control"  size="16" type="text" name="searchable.beginCreatedTime" id="begin_created_time" value="<fmt:formatDate value="${userForm.searchable.beginCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <span class="input-group-addon">结束创建时间：</span>
                                    <div class="input-group date form_datetime">
                                        <input class="form-control"  size="16" type="text" name="searchable.endCreatedTime" id="end_created_time" value="<fmt:formatDate value="${userForm.searchable.endCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row wrapper">
                            <div class="col-sm-3 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon ">是否有效：</span>
                                    <form:select path="userForm.searchable.valid" items="${queryEnableDisableStatusList}" itemValue="value" class="form-control" itemLabel="name"/>
                                </div>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <div class="input-group">
                                    <span class="input-group-addon ">排序：</span>
                                    <form:select path="userForm.orderField" items="${orderFieldMap}" class="form-control"/>
                                </div>
                                <div class="input-group">
                                    <form:select path="userForm.orderDirection" items="${orderDirectionList}" itemValue="value" class="form-control" itemLabel="name"/>
                                </div>
                            </div>
                            <div class="col-sm-1 m-b-xs">
                                <input class="btn btn-sm btn-default" id="query" name="query" type="submit" value="查询" />
                            </div>
                        </div>
                    </form>
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
                    <div class="col-sm-4 text-right text-center-xs">
                        <form id="pageForm" class="form-inline" action="${ctx}/user/list" method="post">
                            <input id="page" type="hidden" name="currentPage">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <c:if test="${userForm.currentPage > 1}">
                                    <li><a style="cursor:pointer;" onclick="goPage(${userForm.currentPage - 1});"><i class="fa fa-chevron-left"></i></a></li>
                                </c:if>
                                <c:forEach begin="1" end="${userForm.totalPages}" var="i">
                                    <li><a onclick="goPage(${i});" style="cursor:pointer;<c:if test="${userForm.currentPage == i}"> background-color:#EEE;</c:if>">${i}</a></li>
                                </c:forEach>
                                <c:if test="${userForm.currentPage < userForm.totalPages}">
                                    <li><a style="cursor:pointer;" onclick="goPage(${userForm.currentPage + 1});"><i class="fa fa-chevron-right"></i></a></li>
                                </c:if>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>