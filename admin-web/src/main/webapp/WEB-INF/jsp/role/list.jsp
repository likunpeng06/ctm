<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading"><a href="${ctx}/role/create" class="btn btn-primary btn-sm">新建角色</a></div>
            <div class="panel-body">
                <form role="form" id="queryForm" class="form-inline" action="${ctx}/role/list" method="post">
                    <div class="row">
                        <div class="form-group col-sm-3">
                            <label for="roleid" class="control-label">编码：</label>
                            <input type="text" name="id" id="roleid" value="${roleSearchable.id}" class="form-control input-sm">
                        </div>
                        <div class="form-group col-sm-3">
                            <label for="identifier" class="control-label">标识符：</label>
                            <input type="text" name="identifier" id="identifier" value="${roleSearchable.identifier}" class="form-control input-sm">
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
            <div class="panel-heading">角色列表</div>
            <c:if test="${page.content != null}">
            <table class="table table-condensed table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-no">序号</th>
                    <th>编号</th>
                    <th>标识符</th>
                    <th>角色名</th>
                    <th>是否默认角色</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>是否有效</th>
                    <th colspan="3">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="role" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${role.id}</td>
                        <td><a href="${ctx}/role/view/${role.id}">${role.identifier}</a></td>
                        <td>${role.name}</td>
                        <td>${role.isDefault.name}</td>
                        <td><fmt:formatDate value="${role.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${role.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${role.valid.name}</td>
                        <td><a href="${ctx}/role/update/${role.id}">修改</a></td>
                        <td><a href="${ctx}/role/allot/${role.id}">分配资源</a></td>
                        <td><a href="${ctx}/role/delete/${role.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:if>
        </div>
        <frame:pagination page="${page}" />
    </div>
</frame:frame>