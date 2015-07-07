<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">新建游戏</div>
            <div class="panel-body">
            <form:form class="form-horizontal" action="${ctx}/game/create" method="post" commandName="gameForm">
                <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                <div class="form-group">
                    <label class="col-sm-3 control-label">游戏名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="game.name" value="${gameForm.game.name}" autofocus="" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">iconUrl</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="game.iconUrl" value="${gameForm.game.iconUrl}" required="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">largeImgUrl</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="game.largeImgUrl" value="${gameForm.game.largeImgUrl}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">gameUrl</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="game.gameUrl" value="${gameForm.game.gameUrl}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">描述</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" name="game.description">${gameForm.game.description}</textarea>
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