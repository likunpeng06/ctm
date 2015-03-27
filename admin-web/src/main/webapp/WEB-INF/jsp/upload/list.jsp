<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading"><a href="${ctx}/upload/create" class="btn btn-primary btn-sm">新建上传文件</a></div>

        </div>
    </div>

    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">上传文件列表</div>
            <c:if test="${page.content != null}">
            <table class="table table-condensed table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-no">序号</th>
                    <th>编号</th>
                    <th>上传文件名</th>
                    <th>url</th>
                    <th>描述</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="uploadFile" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${uploadFile.id}</td>
                        <td><a href="${ctx}/upload/view/${uploadFile.id}">${uploadFile.name}</a></td>
                        <td>${uploadFile.url}</td>
                        <td>${uploadFile.description}</td>
                        <td><fmt:formatDate value="${uploadFile.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${uploadFile.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

                        <td><a href="${ctx}/upload/delete/${uploadFile.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:if>
        </div>
        <frame:pagination page="${page}" />
    </div>
</frame:frame>