<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">查看用户</div>
            <table class="table table-condensed table-striped table-bordered table-hover">
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
                    <td>角色</td>
                    <td><c:forEach items="${userForm.user.roles}" var="role">${role.name}&nbsp;&nbsp;</c:forEach></td>
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
        </div>
        <div class="text-center">
            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
        </div>
    </div>
</frame:frame>