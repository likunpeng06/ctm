<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading"><a href="${ctx}/resource/create" class="btn btn-primary btn-sm">新建一级资源</a></div>
            <div class="panel-body">
                <form resource="form" id="queryForm" class="form-inline" action="${ctx}/resource/list" method="post">
                    <div class="row">
                        <div class="form-group col-sm-3">
                            <label for="resourceid" class="control-label">资源编码：</label>
                            <input type="text" name="id" id="resourceid" value="${resourceSearchable.id}" class="form-control input-sm">
                        </div>
                        <div class="form-group col-sm-3">
                            <label for="name" class="control-label">资源名称：</label>
                            <input type="text" name="name" id="name" value="${resourceSearchable.name}" class="form-control input-sm">
                        </div>
                        <div class="col-sm-6">
                            <button id="query" class="btn btn-primary" type="submit">查询</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">资源列表</div>
            <c:if test="${page.content != null}">
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
                    <th colspan="3">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="resource" varStatus="status">
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
                        <td><a href="${ctx}/resource/delete/${resource.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:if>
        </div>
        <frame:pagination page="${page}" />
    </div>
</frame:frame>