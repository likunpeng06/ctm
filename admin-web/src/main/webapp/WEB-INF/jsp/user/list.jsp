<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
<div class="container-fluid">
    <a href="${ctx}/user/create" class="btn btn-default btn-sm">新建用户</a>
</div>
<div class="container-fluid">
    <form role="form" class="form-inline" id="queryForm" action="${ctx}/user/list" method="post">
        <div class="row wrapper">
            <div class="col-sm-3 m-b-xs">
                <div class="form-group">
                    <label for="userid" class="control-label">用户编码：</label>
                    <input type="text" name="id" id="userid" value="${userSearchable.id}" class="form-control input-sm">
                </div>
            </div>
            <div class="col-sm-3 m-b-xs">
                <div class="form-group">
                    <label for="username" class="control-label">用户名：</label>
                    <input type="text" name="username" id="username" value="${userSearchable.username}" class="form-control input-sm">
                </div>
            </div>
            <div class="col-sm-3 m-b-xs">
                <div class="form-group">
                    <label for="email" class="control-label">邮箱：</label>
                    <input type="text" name="email" id="email" value="${userSearchable.email}" class="form-control input-sm">
                </div>
            </div>
            <div class="col-sm-3 m-b-xs">
                <div class="form-group">
                    <label for="mobile" class="control-label">手机号：</label>
                    <input type="text" name="mobile" id="mobile" value="${userSearchable.mobile}" class="form-control input-sm">
                </div>
            </div>
        </div>
        <div class="row wrapper">
            <div class="col-sm-6 m-b-xs">
                <div class="form-group">
                    <label for="begin_created_time" class="control-label">开始创建时间：</label>
                    <div class="input-group date form_datetime input-group-sm">
                        <input class="form-control"  size="16" type="text" name="beginCreatedTime" id="begin_created_time" value="<fmt:formatDate value="${userSearchable.beginCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 m-b-xs">
                <div class="form-group">
                    <label for="end_created_time" class="control-label">结束创建时间：</label>
                    <div class="input-group date form_datetime input-group-sm">
                        <input class="form-control"  size="16" type="text" name="endCreatedTime" id="end_created_time" value="<fmt:formatDate value="${userSearchable.endCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row wrapper">
            <div class="col-sm-3 m-b-xs">
                <div class="form-group">
                    <label for="valid" class="control-label">是否有效：</label>
                    <form:select id="valid" path="userSearchable.valid" items="${queryEnableDisableStatusList}" itemValue="value" cssClass="form-control input-sm" itemLabel="name"/>
                </div>
            </div>
            <div class="col-sm-3 m-b-xs">
                <div class="form-group">
                    <label for="sort_query" class="control-label">排序：</label>
                    <input id="sort_query" type="text" name="sort" value="${userSearchable.sort}" class="form-control input-sm"/>
                </div>
            </div>
            <div class="col-sm-6 m-b-xs">
                <button id="query" class="btn btn-default" type="submit">查询</button>
            </div>
        </div>
    </form>
</div>

<div class="container-fluid">
    <div class="panel panel-default">
        <div class="panel-heading">用户列表</div>
        <c:if test="${page.content != null}">
        <table class="table table-condensed table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th class="col-no">序号</th>
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
            <c:forEach items="${page.content}" var="user" varStatus="status">
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
                    <td><a href="${ctx}/user/delete/${user.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
    </div>
    <frame:pagination page="${page}" />
</div>

<script type="text/javascript">
    $(function() {
        $(".form_datetime").datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            minuteStep: 1,
            todayBtn:  1,
            autoclose: 1
        });
    });
</script>
</frame:frame>