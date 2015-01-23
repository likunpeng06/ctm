<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>后台管理系统<c:if test="${!empty title}">--${title}</c:if></title>
		<link href="${ctx}/resources/css/bootstrap.min.css" rel="stylesheet">
		<%--<link href="${ctx}/resources/css/bootstrap${theme}.css" rel="stylesheet">--%>
		<link href="${ctx}/resources/css/main.css" rel="stylesheet">
		<link rel="stylesheet" href="${ctx}/resources/css/datetimepicker/bootstrap-datetimepicker.min.css">

		<!--[if lt IE 9]>
		<script src="${ctx}/resources/js/html5shiv.min.js"></script>
		<script src="${ctx}/resources/js/respond.min.js"></script>
		<![endif]-->
		<script src="${ctx}/resources/js/jquery-1.11.1.min.js"></script>
		<script src="${ctx}/resources/js/bootstrap.min.js"></script>
		<script src="${ctx}/resources/js/jquery.validate.min.js"></script>
		<script src="${ctx}/resources/js/common.js"></script>
		<script src="${ctx}/resources/js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
		<script src="${ctx}/resources/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${ctx}/dashboard">后台管理系统</a>
			</div>
			<div id="navbar-collapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> qatang <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">个人信息</a></li>
							<li><a href="#">更改密码</a></li>
							<li><a href="#">站内消息</a></li>
							<li class="divider"></li>
							<li><a href="${ctx}/signout" class="navbar-link confirm" data-confirm="确认要退出吗?"><span
									class="glyphicon glyphicon-log-out"></span> 退出 </a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<c:if test="${!empty menus}">
			<div class="col-md-2">
				<c:forEach items="${menus}" var="menu">
				<div class="panel panel-default">
					<div class="panel-heading">${menu.name}</div>
					<ul class="list-group list-unstyled">
						<c:forEach items="${menu.children}" var="resource">
						<li><a href="${ctx}${resource.url}" class="list-group-item ${resource eq currentResource?'active':''}">${resource.name}</a></li>
						</c:forEach>
					</ul>
				</div>
				</c:forEach>
			</div>
			</c:if>
			<div class="col-md-${empty menus?'12':'10'}">
				<c:if test="${!empty breadcrumb}">
				<ol class="breadcrumb">
					<c:forEach items="${breadcrumb}" var="resource">
					<li><a href="${ctx}${resource.url}">${resource.name}</a></li>
					</c:forEach>
				</ol>
				</c:if>
				<jsp:doBody />
			</div>
		</div>
	</div>
</body>
</html>