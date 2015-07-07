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
                        <td>${logForm.log.id}</td>
                    </tr>
                    <tr>
                        <td>用户名</td>
                        <td>${logForm.log.user.username}</td>
                    </tr>
                    <tr>
                        <td>链接</td>
                        <td>${logForm.log.url}</td>
                    </tr>
                    <tr>
                        <td>参数</td>
                        <td>${logForm.log.params}</td>
                    </tr>
                    <tr>
                        <td>创建时间</td>
                        <td><fmt:formatDate value="${logForm.log.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="text-center">
            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
        </div>
    </div>
</frame:frame>