<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">修改角色</div>
            <div class="panel-body">
                <form:form class="form-horizontal" action="${ctx}/role/update" method="post" commandName="roleForm">
                    <input type="hidden" name="role.id" value="${roleForm.role.id}"/>
                    <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标识符</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="role.identifier" value="${roleForm.role.identifier}" autofocus="" required="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">角色名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="role.name" value="${roleForm.role.name}" required="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">描述</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" name="role.description">${roleForm.role.description}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否默认角色</label>
                        <div class="col-sm-6 form-inline">
                            <form:select cssClass="form-control" path="role.isDefault" items="${yesNoStatusList}" itemValue="value" itemLabel="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否有效</label>
                        <div class="col-sm-6 form-inline">
                            <form:select cssClass="form-control" path="role.valid" items="${enableDisableStatusList}" itemValue="value" itemLabel="name"/>
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