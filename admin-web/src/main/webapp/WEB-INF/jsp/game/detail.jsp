<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">查看游戏</div>
            <table class="table table-condensed table-striped table-bordered table-hover">
                <tbody>
                <tr>
                    <td>编码</td>
                    <td>${gameForm.game.id}</td>
                </tr>
                <tr>
                    <td>游戏名</td>
                    <td>${gameForm.game.name}</td>
                </tr>
                <tr>
                    <td>iconUrl</td>
                    <td>${gameForm.game.iconUrl}</td>
                </tr>
                <tr>
                    <td>largeImgUrl</td>
                    <td>${gameForm.game.largeImgUrl}</td>
                </tr>
                <tr>
                    <td>gameUrl</td>
                    <td>${gameForm.game.gameUrl}</td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>${gameForm.game.description}</td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td><fmt:formatDate value="${gameForm.game.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>修改时间</td>
                    <td><fmt:formatDate value="${gameForm.game.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="text-center">
            <a class="btn btn-primary" href="${ctx}${forwardUrl}">返回列表</a>
        </div>
    </div>
</frame:frame>