<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading"></div>
            <div class="panel-body">
                <form role="form" id="queryForm" class="form-inline" action="${ctx}/log/list" method="post">
                    <div class="row">
                        <div class="form-group col-sm-4">
                            <label for="username" class="control-label">用户名：</label>
                            <input type="text" name="username" id="username" value="${logSearchable.username}" class="form-control input-sm">
                        </div>
                        <div class="form-group col-sm-4">
                            <label for="url" class="control-label">链接：</label>
                            <input type="text" name="url" id="url" value="${logSearchable.url}" class="form-control input-sm">
                        </div>
                        <div class="form-group col-sm-4">
                            <label for="params" class="control-label">参数：</label>
                            <input type="text" name="params" id="params" value="${logSearchable.params}" class="form-control input-sm">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label for="begin_created_time" class="control-label">开始创建时间：</label>
                            <div class="input-group date form_datetime input-group-sm">
                                <input class="form-control"  size="20" type="text" name="beginCreatedTime" id="begin_created_time" value="<fmt:formatDate value="${logSearchable.beginCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label for="end_created_time" class="control-label">结束创建时间：</label>
                            <div class="input-group date form_datetime input-group-sm">
                                <input class="form-control"  size="20" type="text" name="endCreatedTime" id="end_created_time" value="<fmt:formatDate value="${logSearchable.endCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-sm-12">
                            <button id="query" class="btn btn-primary" type="submit">查询</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">日志列表</div>
            <c:if test="${page.content != null}">
            <table class="table table-condensed table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-no">序号</th>
                    <th>编号</th>
                    <th>用户名</th>
                    <th>链接</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="log" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td><a href="${ctx}/log/view/${log.id}">${log.id}</a></td>
                        <td>${log.user.username}</td>
                        <td>${log.url}</td>
                        <td><fmt:formatDate value="${log.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><a href="${ctx}/log/view/${log.id}">查看</a></td>
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