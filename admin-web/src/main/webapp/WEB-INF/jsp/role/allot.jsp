<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<frame:frame>
    <link href="${ctx}/resources/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <script src="${ctx}/resources/js/jquery.ztree.all-3.5.min.js"></script>
    <div class="container-fluid">
        <div class="panel panel-default">
            <div class="panel-heading">分配资源</div>
            <div class="panel-body">
                <form:form class="form-horizontal" action="${ctx}/role/allot" method="post" commandName="roleForm">
                    <input type="hidden" name="role.id" value="${roleForm.role.id}"/>
                    <form:errors path="*" cssClass="alert alert-danger" element="div" role="alert"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标识符</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${roleForm.role.identifier}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">角色名称</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${roleForm.role.name}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">资源</label>
                        <div class="col-sm-6">
                            <ul id="resourceTree" class="ztree"></ul>
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
    <script type="text/javascript">

        var setting = {
            view: {
                fontCss: {'font-family': 'Helvetica, Arial'}
            },
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var data = ${resources};
        $(function(){
            $.fn.zTree.init($("#resourceTree"), setting, data);

            var treeObj = $.fn.zTree.getZTreeObj("resourceTree");

            $("#roleForm").submit(function(){
                var nodes = treeObj.getCheckedNodes(true);
                for(var i = 0;i < nodes.length;i ++){
                    $(this).append('<input type="hidden" name="role.resources[' + i + '].id" value="' + nodes[i].id +'"/>');
                }
            });
        });
    </script>
</frame:frame>