<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">分配角色</div>
            <div class="panel-body">
                <form:form class="form-horizontal" action="${ctx}/user/allot" method="post" commandName="userForm">
                    <input type="hidden" name="user.id" value="${userForm.user.id}"/>
                    <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${userForm.user.username}</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">角色</label>
                        <div class="col-sm-6">
                            <c:forEach items="${roles}" var="role">
                                <label class="checkbox-inline"><input name="user.roles" type="checkbox" value="${role.id}"
                                    ${userForm.user.contains(role)?'checked':''}>${role.name}</label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">提交</button>
                            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</frame:frame>