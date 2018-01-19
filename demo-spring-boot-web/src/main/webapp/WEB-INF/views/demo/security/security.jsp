<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="casper" %>

<casper:Breadcrumbs>
    <li class=""><a href="<c:url value="/demo"/>"><fmt:message key="demo.title"/></a></li>
    <li class="active"><fmt:message key="demo.security.title"/></li>
</casper:Breadcrumbs>

<section class="content">

    <div class="col-md-12">
        <div class="box">
			<p>E-mail: <sec:authentication property="principal.emailAddress"/></p>
			<p>Role: 
				<sec:authorize access="hasRole('ADMIN')">ADMIN</sec:authorize>
				<sec:authorize access="hasRole('SOL_ARCH')">SOL_ARCH</sec:authorize>
				<sec:authorize access="hasRole('MCRSS_ADMIN')">MCRSS_ADMIN</sec:authorize>
				<sec:authorize access="hasRole('VIEWER')">VIEWER</sec:authorize>
				<sec:authorize access="hasRole('USER_ADMIN')">USER_ADMIN</sec:authorize>
				<sec:authorize access="hasRole('SALES_USER')">SALES_USER</sec:authorize>
				<sec:authorize access="hasRole('REG_SUPER_USER')">REG_SUPER_USER</sec:authorize>
				<sec:authorize access="hasRole('HUB_ADMIN')">HUB_ADMIN</sec:authorize>
			</p>
			<sec:authentication property="authorities" var="authorities" scope="page" />
			<p>Authorities:
				<ul>
				    <c:forEach var="authority" items="${authorities}">
				    	<li>${authority}</li>
				    </c:forEach>
				</ul>
			</p>
	   </div>
	</div>
	
    <div class="col-md-6">
		<!-- Records Panel -->
		<div id="box-page-list" class="box box-success">
			<div class="box-header">
				<h3 class="box-title"><fmt:message key="demo.security.title" /></h3>
				<!-- Buttons control panel -->
				<div class="box-tools pull-right">
					<casper:ActionButton id="btn-page-clear" type="clear" />
					<casper:ActionButton id="btn-page-refresh" type="refresh" />
					<casper:ActionButton id="btn-page-new" type="new" access="hasAuthority('CAC_EDIT_DEMO_PAGE')" />
				</div>
			</div>

			<div class="box-body no-padding">
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
		    </div>
		
		    <div class="box-body no-padding">
		        <!-- panel list all records -->
		        <div id="box-page-table" class="table-responsive">
		            <div class="text-center"><fmt:message key="lbl.no.records.found"/></div>
		        </div>
		    </div>
		</div>
		<!-- /.Records Panel -->
    </div>
    
    <div class="col-md-6">
        <casper:CrudRecordPanel title="security.permission.title">
            <input type="hidden" class="form-control" id="id" name="id">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-3 control-label"><fmt:message key="demo.security.lbl.name"/></label>
                    <div class="col-sm-9 col-lg-6">
                        <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="demo.security.lbl.name"/>" />
                    </div>
                </div>
            </div>
        </casper:CrudRecordPanel>
    </div>

</section>

<script lang="javascript">
$(document).ready(function() {
    var settings = {
        apis: {
            restful: { url: '<c:url value="/api/demo/security/"/>' }
        },
        validation: {
            'name': { required: true, minlength: 3, maxlength: 60 }
        }
    };
    
    $.Casper.crud(settings).bind(CasperVoidController).init();
});
</script>

<script id="template-crud-tbl" type="text/html">
<table id="tbl-crud-records" class="table table-hover table-striped">
<tbody>
<tr>
    <th style="width: 10px"><fmt:message key="demo.security.lbl.id"/></th>
    <th><fmt:message key="demo.security.lbl.name"/></th>
    <th style="width: 70px"><fmt:message key="lbl.actions"/></th>
</tr>
{{#data.content}}
    <tr>
        <td>{{id}}</td>
        <td>{{name}}</td>
        <td>
            <a href="#" data-id="{{id}}" name="btn-page-delete" id="btn-page-delete-{{id}}"><i class="fa fa-trash-o pull-right text-red"></i></a>
            <sec:authorize access="hasAuthority('CAC_EDIT_DEMO_PAGE')">
                <a href="#" data-id="{{id}}" name="btn-page-edit" id="btn-page-edit-{{id}}"><i class="fa fa-edit pull-right text-blue"></i></a>
            </sec:authorize>
            <a href="#" data-id="{{id}}" name="btn-page-view" id="btn-page-view-{{id}}"><i class="fa  fa-folder-open-o pull-right text-green"></i></a>    
        </td>
    </tr>
{{/data.content}}

{{^data.content}}
    <tr><td colspan="4" class="text-center"><fmt:message key="lbl.no.records.found"/></td></tr>
{{/data.content}}
</tbody>
</table>
</script>