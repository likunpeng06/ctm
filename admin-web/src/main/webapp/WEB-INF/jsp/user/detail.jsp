<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>用户详情</title>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="keywords" content="" />
        <meta http-equiv="description" content="" />
        <link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx}/resources/css/main.css">
        <script src="${ctx}/resources/js/jquery-1.11.1.min.js"></script>
        <script src="${ctx}/resources/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">后台管理系统</a>
                </div>
            </div>
        </div>
        <div class="container" style="margin-top:70px;">
            <div class="text-center">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <td>编码</td>
                            <td>${userForm.user.id}</td>
                        </tr>
                        <tr>
                            <td>用户名</td>
                            <td>${userForm.user.username}</td>
                        </tr>
                        <tr>
                            <td>邮箱</td>
                            <td>${userForm.user.email}</td>
                        </tr>
                        <tr>
                            <td>手机号</td>
                            <td>${userForm.user.mobile}</td>
                        </tr>
                        <tr>
                            <td>创建时间</td>
                            <td><fmt:formatDate value="${userForm.user.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td>修改时间</td>
                            <td><fmt:formatDate value="${userForm.user.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td>是否有效</td>
                            <td>${userForm.user.valid.name}</td>
                        </tr>
                        <tr>
                            <td>是否邮箱已验证</td>
                            <td>${userForm.user.emailValid.name}</td>
                        </tr>
                        <tr>
                            <td>是否手机已验证</td>
                            <td>${userForm.user.mobileValid.name}</td>
                        </tr>
                    </tbody>
                </table>
                <a class="btn btn-primary btn-sm" href="${ctx}${forwardUrl}">返回列表</a>
            </div>
        </div>
    </body>
</html>