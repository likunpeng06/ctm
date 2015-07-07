<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <c:if test="${empty parentResource}">
        <a href="${ctx}/resource/create" class="btn btn-primary btn-sm">新建一级资源</a>
        </c:if>
        <c:if test="${!empty parentResource}">
            <a href="${ctx}/resource/create?resource.parent.id=${parentResource.id}" class="btn btn-primary btn-sm">新建<${parentResource.name}>子资源</a>
        </c:if>
    </div>
    <br>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading"><c:if test="${!empty parentResource}"><${parentResource.name}>子</c:if>资源列表</div>
            <c:if test="${resourceList != null}">
            <table class="table table-condensed table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>资源名</th>
                    <th>标识符</th>
                    <th>url</th>
                    <th>类型</th>
                    <th>级别</th>
                    <th>优先级</th>
                    <th>是否有效</th>
                    <th colspan="4">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resourceList}" var="resource" varStatus="status">
                    <tr>
                        <td>
                            <c:forEach begin="0" end="${resource.treeLevel}">&nbsp;&nbsp;</c:forEach>
                            <a href="${ctx}/resource/view/${resource.id}">${resource.name}</a>
                        </td>
                        <td>${resource.identifier}</td>
                        <td>${resource.url}</td>
                        <td>${resource.type.name}</td>
                        <td>${resource.treeLevel}</td>
                        <td>${resource.priority}</td>
                        <td>${resource.valid.name}</td>
                        <td><a href="${ctx}/resource/update/${resource.id}">修改</a></td>
                        <td>
                            <c:if test="${!resource.end}">
                            <a href="${ctx}/resource/create?resource.parent.id=${resource.id}">添加子资源</a>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${!resource.end}">
                                <a href="${ctx}/resource/list?resource.parent.id=${resource.id}">查看子资源</a>
                            </c:if>
                        </td>
                        <td><a href="${ctx}/resource/delete/${resource.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:if>
        </div>
    </div>
</frame:frame>