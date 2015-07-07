<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">修改用户</div>
            <div class="panel-body">
                <form:form class="form-horizontal" action="${ctx}/user/update" method="post" commandName="userForm">
                    <input type="hidden" name="user.id" value="${userForm.user.id}"/>
                    <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${userForm.user.username}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">邮箱</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="user.email" value="${userForm.user.email}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">手机号</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="user.mobile" value="${userForm.user.mobile}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否有效</label>
                        <div class="col-sm-6 form-inline">
                            <form:select cssClass="form-control" path="user.valid" items="${enableDisableStatusList}" itemValue="value" itemLabel="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否邮箱已验证</label>
                        <div class="col-sm-6 form-inline">
                            <form:select cssClass="form-control" path="user.emailValid" items="${yesNoStatusList}" itemValue="value" itemLabel="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否手机已验证</label>
                        <div class="col-sm-6 form-inline">
                            <form:select cssClass="form-control" path="user.mobileValid" items="${yesNoStatusList}" itemValue="value" itemLabel="name"/>
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