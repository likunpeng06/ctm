<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">查看资源</div>
            <table class="table table-condensed table-striped table-bordered table-hover">
                <tbody>
                <tr>
                    <td>编码</td>
                    <td>${resourceForm.resource.id}</td>
                </tr>
                <tr>
                    <td>父级资源</td>
                    <td>${resourceForm.resource.parent.name}</td>
                </tr>
                <tr>
                    <td>标识符</td>
                    <td>${resourceForm.resource.identifier}</td>
                </tr>
                <tr>
                    <td>资源名</td>
                    <td>${resourceForm.resource.name}</td>
                </tr>
                <tr>
                    <td>资源链接</td>
                    <td>${resourceForm.resource.url}</td>
                </tr>
                <tr>
                    <td>资源类型</td>
                    <td>${resourceForm.resource.type.name}</td>
                </tr>
                <tr>
                    <td>级别</td>
                    <td>${resourceForm.resource.treeLevel}</td>
                </tr>
                <tr>
                    <td>优先级</td>
                    <td>${resourceForm.resource.priority}</td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td><fmt:formatDate value="${resourceForm.resource.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>修改时间</td>
                    <td><fmt:formatDate value="${resourceForm.resource.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>是否有效</td>
                    <td>${resourceForm.resource.valid.name}</td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td>${resourceForm.resource.memo}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="text-center">
            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
        </div>
    </div>
</frame:frame>