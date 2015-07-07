<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">查看上传文件</div>
            <table class="table table-condensed table-striped table-bordered table-hover">
                <tbody>
                <tr>
                    <td>编码</td>
                    <td>${uploadFileForm.uploadFile.id}</td>
                </tr>
                <tr>
                    <td>上传文件名</td>
                    <td>${uploadFileForm.uploadFile.name}</td>
                </tr>
                <tr>
                    <td>iconUrl</td>
                    <td>${uploadFileForm.uploadFile.url}</td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>${uploadFileForm.uploadFile.description}</td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td><fmt:formatDate value="${uploadFileForm.uploadFile.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>修改时间</td>
                    <td><fmt:formatDate value="${uploadFileForm.uploadFile.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="text-center">
            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
        </div>
    </div>
</frame:frame>