<%@ tag %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="casper" tagdir="/WEB-INF/tags" %>

<%@ attribute name="title" required="true" type="String" %>
<%@ attribute name="searchBoxProvided" required="false" type="Boolean" %>

<!-- Records Panel -->
<div id="box-page-list" class="box box-success">
    <div class="box-header">
        <h3 class="box-title"><fmt:message key="${title}"/></h3>
        <!-- Buttons control panel -->
        <div class="box-tools pull-right">
            <casper:ActionButton id="btn-page-clear" type="clear"/>
            <casper:ActionButton id="btn-page-refresh" type="refresh"/>
            <casper:ActionButton id="btn-page-new" type="new"/>
        </div>
    </div>

    <div class="box-body no-padding">
        <c:if test="${empty searchBoxProvided || !searchBoxProvided}">
        <div id="box-page-list-search" class="row mailbox-controls">
                <!-- Search component -->
                <div class="col-md-6">
                    <form class="form-horizontal" id="frm-page-search">
                    <div class="input-group input-group-md">
                        <input type="hidden" name="page" id="frm-page-number" />
                        <input type="text" name="filter" id="frm-page-filter" class="form-control input-sm" placeholder="<fmt:message key="placeholder.search"/>">
                        <span class="input-group-btn">
                            <button id="btn-page-search" type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
                        </span>
                    </div>
                    </form>
                </div>
                <!-- Pagination component -->
                <div class="col-md-6">
                    <div class="btn-group pull-right">
                        <casper:Pagination id="box-page-pagination"/>
                    </div>
                </div>
        </div>
        </c:if>
        <c:if test="${not empty searchBoxProvided && searchBoxProvided}">
<jsp:doBody/>
        </c:if>
    </div>

    <div class="box-body no-padding">
        <!-- panel list all records -->
        <div id="box-page-table" class="table-responsive">
            <div class="text-center"><fmt:message key="lbl.no.records.found"/></div>
        </div>
    </div>
</div>
<!-- /.Records Panel -->