<%@ tag %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ attribute name="id" required="true" type="String" %>
<%@ attribute name="type" required="true" type="String" %>
<%@ attribute name="icon" required="false" type="String" %>
<%@ attribute name="hideIcon" required="false" type="Boolean" %>
<%@ attribute name="position" required="false" type="String" %>
<%@ attribute name="access" required="false" type="String" %>

<c:if test="${position != null}">
    <c:set var="position" value="pull-${position}" />
</c:if>

<c:if test="${icon eq null}">
    <c:if test="${type eq 'clear'}">
        <c:set var="icon" value="fa fa-undo" />
    </c:if>
    
    <c:if test="${type eq 'refresh'}">
        <c:set var="icon" value="fa fa-refresh" />
    </c:if>
    
    <c:if test="${type eq 'new'}">
        <c:set var="icon" value="fa fa-file-o" />
    </c:if>
    
    <c:if test="${type eq 'save'}">
        <c:set var="icon" value="fa fa-save" />
    </c:if>
    
    <c:if test="${type eq 'cancel'}">
        <c:set var="icon" value="fa fa-times" />
    </c:if>
    
    <c:if test="${type eq 'delete'}">
        <c:set var="icon" value="fa fa-trash-o" />
    </c:if>
</c:if>

<c:if test="${type eq 'clear'}">
    <c:set var="__btn_i18n_label_name" value="btn.lbl.clear" />
    <c:set var="__btn_class_name" value="btn btn-warning btn-sm" />
</c:if>
    
<c:if test="${type eq 'refresh'}">
    <c:set var="__btn_i18n_label_name" value="btn.lbl.refresh" />
    <c:set var="__btn_class_name" value="btn btn-info btn-sm" />
</c:if>

<c:if test="${type eq 'new'}">
    <c:set var="__btn_i18n_label_name" value="btn.lbl.new" />
    <c:set var="__btn_class_name" value="btn btn-success btn-sm" />
</c:if>

<c:if test="${type eq 'save'}">
    <c:set var="__btn_i18n_label_name" value="btn.lbl.save" />
    <c:set var="__btn_class_name" value="btn btn-primary btn-sm" />
</c:if>

<c:if test="${type eq 'cancel'}">
    <c:set var="__btn_i18n_label_name" value="btn.lbl.cancel" />
    <c:set var="__btn_class_name" value="btn btn-default btn-sm" />
</c:if>

<c:if test="${type eq 'delete'}">
    <c:set var="__btn_i18n_label_name" value="btn.lbl.delete" />
    <c:set var="__btn_class_name" value="btn btn-danger btn-sm" />
</c:if>

<sec:authorize access="${empty access ? 'permitAll' : access}">
    <button id="${id}" type="button" class="${__btn_class_name} ${position}"><c:if test="${not hideIcon}"><i class="${icon}"></i></c:if> <fmt:message key="${__btn_i18n_label_name}"/></button>    
</sec:authorize>
