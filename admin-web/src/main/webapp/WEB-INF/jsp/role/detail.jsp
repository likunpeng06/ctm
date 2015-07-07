<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">查看角色</div>
            <table class="table table-condensed table-striped table-bordered table-hover">
                <tbody>
                <tr>
                    <td>编码</td>
                    <td>${roleForm.role.id}</td>
                </tr>
                <tr>
                    <td>标识符</td>
                    <td>${roleForm.role.identifier}</td>
                </tr>
                <tr>
                    <td>角色名</td>
                    <td>${roleForm.role.name}</td>
                </tr>
                <tr>
                    <td>是否默认角色</td>
                    <td>${roleForm.role.isDefault.name}</td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td><fmt:formatDate value="${roleForm.role.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>修改时间</td>
                    <td><fmt:formatDate value="${roleForm.role.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>是否有效</td>
                    <td>${roleForm.role.valid.name}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="text-center">
            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
        </div>
    </div>
</frame:frame>