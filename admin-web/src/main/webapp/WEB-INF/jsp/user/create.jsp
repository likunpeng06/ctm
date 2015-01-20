<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">新建用户</div>
            <div class="panel-body">
            <form:form class="form-horizontal" action="${ctx}/user/create" method="post" commandName="userForm">
                <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                <div class="form-group">
                    <label class="col-sm-3 control-label">用户名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="user.username" value="${userForm.user.username}" autofocus="" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" name="user.password" value="${userForm.user.password}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" name="conPassword" value="${userForm.conPassword}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">邮箱</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="user.email" value="${userForm.user.email}" required="">
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