<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">新建上传文件</div>
            <div class="panel-body">
            <form:form class="form-horizontal" action="${ctx}/upload/create" method="post" commandName="uploadForm">
                <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                <div class="form-group">
                    <label class="col-sm-3 control-label">上传文件名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="uploadFile.name" value="${uploadForm.uploadFile.name}" autofocus="" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">url</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="uploadFile.url" value="${uploadForm.uploadFile.url}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">描述</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" name="uploadFile.description">${uploadForm.uploadFile.description}</textarea>
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