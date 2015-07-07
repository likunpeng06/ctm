<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame title="新建资源">
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">新建资源</div>
            <div class="panel-body">
            <form:form class="form-horizontal" action="${ctx}/resource/create" method="post" commandName="resourceForm">
                <input type="hidden" name="resource.parent.id" value="${resourceForm.resource.parent.id}" />
                <form:errors path="*" cssClass="alert alert-danger" element="div" resource="alert"/>
                <div class="form-group">
                    <label class="col-sm-3 control-label">标识符</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="resource.identifier" value="${resourceForm.resource.identifier}" autofocus="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">资源名称</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="resource.name" value="${resourceForm.resource.name}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">资源链接</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="resource.url" value="${resourceForm.resource.url}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">优先级</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="resource.priority" value="${resourceForm.resource.priority}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">资源类型</label>
                    <div class="col-sm-6 form-inline">
                        <form:select cssClass="form-control" path="resource.type" items="${resourceTypeList}" itemValue="value" itemLabel="name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">是否有效</label>
                    <div class="col-sm-6 form-inline">
                        <form:select cssClass="form-control" path="resource.valid" items="${enableDisableStatusList}" itemValue="value" itemLabel="name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" name="resource.memo">${resourceForm.resource.memo}</textarea>
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