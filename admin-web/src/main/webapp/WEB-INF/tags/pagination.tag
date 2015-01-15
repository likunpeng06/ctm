<%@tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    int range = 4;

    int begin = 0;
    int end = 0;

    int pageNumber = page.getNumber();
    int totalPages = page.getTotalPages();

    if (range >= totalPages) {
        begin = 0;
        end = totalPages - 1;
    } else {
        int avg = Math.floorDiv(range, 2);
        begin = pageNumber - avg < begin ? begin : pageNumber - avg;
        end = begin + range;
        if (end >= totalPages) {
            end = totalPages - 1;
            begin = end - range;
        }
    }

    request.setAttribute("begin", begin);
    request.setAttribute("end", end);

%>

<form id="pageForm" class="form-inline" action="${ctx}/user/list" method="post">
    <input id="page" type="hidden" name="page" />
    <input id="sort" type="hidden" name="sort" />
    <ul class="pagination pagination-panel pagination-sm">
        <li><a href="#" onclick="goPage(0);">首页</a></li>
        <c:forEach begin="${begin}" end="${end}" var="i">
            <c:if test="${page.number == i}">
            <li class="active"><a href="#" onclick="goPage(${i});"><span>${i + 1} <span class="sr-only">(current)</span></span></a></li>
            </c:if>
            <c:if test="${page.number != i}">
            <li><a href="#" onclick="goPage(${i});">${i + 1}</a></li>
            </c:if>
        </c:forEach>
        <li><a href="#" onclick="goPage(${page.totalPages - 1});">尾页</a></li>
        <li><span>共${page.totalPages}页，每页${page.size}条，总共${page.totalElements}条</span></li>
    </ul>
</form>

<script type="text/javascript">
    var goPage = function(page) {
        var sort = '${page.sort}';
        var arr = sort.split(":");
        var sort_val = arr[0].concat(',').concat(arr[1].trim().toLowerCase());

        $("#page").val(page);
        $("#sort").val(sort_val);
        $("#pageForm").submit();
    }
</script>

