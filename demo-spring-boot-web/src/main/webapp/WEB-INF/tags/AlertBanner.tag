<%@ tag %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="type" required="true" type="String" %>
<%@ attribute name="title" required="true" type="String" %>

<c:choose>
    <c:when test="${type eq 'error'}">
        <c:set var="__cssAlertType" value="danger" />
    </c:when>
    <c:when test="${type eq 'warn'}">
        <c:set var="__cssAlertType" value="warning" />
    </c:when>
    <c:when test="${type eq 'success'}">
        <c:set var="__cssAlertType" value="success" />
    </c:when>
    <c:otherwise>
        <c:set var="__cssAlertType" value="info" />
    </c:otherwise>
</c:choose>

<script id="alert-${type}-template" type="text/html">
    <div id="box-alert-${type}-message-inner" class="box-body">
    <div class="callout alert alert-${__cssAlertType} alert-dismissible">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true"><span clas="text-white">×</span></button>
        <h4><i class="icon fa fa-check"></i> ${title}</h4>
        <!-- {{code}} - {{err}} -->
        {{message}}
    </div>
    </div>
</script>