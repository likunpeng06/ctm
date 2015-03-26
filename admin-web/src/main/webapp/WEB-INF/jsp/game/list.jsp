<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading"><a href="${ctx}/game/create" class="btn btn-primary btn-sm">新建游戏</a></div>
            <div class="panel-body">
                <form role="form" id="queryForm" class="form-inline" action="${ctx}/game/list" method="post">
                    <div class="row">
                        <div class="form-group col-sm-3">
                            <label for="gameid" class="control-label">编码：</label>
                            <input type="text" name="id" id="gameid" value="${gameSearchable.id}" class="form-control input-sm">
                        </div>
                        <div class="form-group col-sm-3">
                            <label for="name" class="control-label">游戏名：</label>
                            <input type="text" name="name" id="name" value="${gameSearchable.name}" class="form-control input-sm">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label for="begin_created_time" class="control-label">开始创建时间：</label>
                            <div class="input-group date form_datetime input-group-sm">
                                <input class="form-control"  size="20" type="text" name="beginCreatedTime" id="begin_created_time" value="<fmt:formatDate value="${gameSearchable.beginCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label for="end_created_time" class="control-label">结束创建时间：</label>
                            <div class="input-group date form_datetime input-group-sm">
                                <input class="form-control"  size="20" type="text" name="endCreatedTime" id="end_created_time" value="<fmt:formatDate value="${gameSearchable.endCreatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="form-group col-sm-3">
                            <label for="sort_query" class="control-label">排序：</label>
                            <form:select id="sort_query" path="gameSearchable.sort" items="${orderFieldMap}" cssClass="form-control input-sm"/>
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
            <div class="panel-heading">游戏列表</div>
            <c:if test="${page.content != null}">
            <table class="table table-condensed table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-no">序号</th>
                    <th>编号</th>
                    <th>游戏名</th>
                    <th>iconUrl</th>
                    <th>largeImgUrl</th>
                    <th>gameUrl</th>
                    <th>描述</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="game" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${game.id}</td>
                        <td><a href="${ctx}/game/view/${game.id}">${game.name}</a></td>
                        <td>${game.iconUrl}</td>
                        <td>${game.largeImgUrl}</td>
                        <td>${game.gameUrl}</td>
                        <td>${game.description}</td>
                        <td><fmt:formatDate value="${game.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${game.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

                        <td><a href="${ctx}/game/update/${game.id}">修改</a></td>
                        <td><a href="${ctx}/game/delete/${game.id}" onclick="return confirm('确实要删除吗？');">删除</a></td>
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