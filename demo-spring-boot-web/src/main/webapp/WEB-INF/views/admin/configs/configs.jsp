<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="casper" tagdir="/WEB-INF/tags" %>

<script src="<c:url value="/plugins/select2/select2.full.min.js"/>"></script>

<casper:Breadcrumbs>
    <li class=""><a href="<c:url value="/admin"/>"><fmt:message key="admin.title"/></a></li>
    <li class="active"><fmt:message key="admin.configs.title"/></li>
</casper:Breadcrumbs>

<section class="content">

    <div class="col-md-6">
        <casper:CrudRecordsPanel title="admin.configs.title" searchBoxProvided="true">
            <div class="row">
                <div class="col-md-12">
                    <form class="form-horizontal" id="frm-page-search">
                    <input type="hidden" name="page" id="frm-page-number" />
                    <div class="col-md-5">
                        <select class="form-control input-sm select2" id="frm-record-context" name="context" data-placeholder="[Filter by Context]">
                            <option value=""></option>
                            <!--
                            <c:forEach items="${contexts}" var="ctx">
                            <option value="${ctx}">${ctx}</option>
                            </c:forEach>
                            -->
                        </select>
                    </div>
                    <div class="form-group col-md-6">
                        <input type="text" class="form-control input-sm" name="filter" id="frm-page-filter" placeholder="<fmt:message key="placeholder.search"/>">
                    </div>
                    <div class="form-group col-md-1">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary btn-sm" id="btn-page-search"><i class="fa fa-search"></i></button>
                        </span>
                    </div>
                    </form>
                </div>
                <!-- Pagination component -->
                <div class="col-md-12">
                    <div class="btn-group pull-right">
                        <casper:Pagination id="box-page-pagination"/>
                    </div>
                </div>
            </div>
        </casper:CrudRecordsPanel>
    </div>

    <div class="col-md-6">
        <casper:CrudRecordPanel title="admin.config.title">
            <!-- Global Configuration Record Form Fields -->
            <input type="hidden" class="form-control" id="id" name="id">
            <div class="box-body">
                <div class="form-group">
                    <label for="description" class="col-sm-3 control-label"><fmt:message key="admin.configs.lbl.context"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="context" name="context" placeholder="<fmt:message key="admin.configs.lbl.context"/>" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-3 control-label"><fmt:message key="admin.configs.lbl.name"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="admin.configs.lbl.name"/>" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-3 control-label"><fmt:message key="admin.configs.lbl.value"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="value" name="value" placeholder="<fmt:message key="admin.configs.lbl.value"/>" />
                    </div>
                </div>
            </div>
        </casper:CrudRecordPanel>
    </div>
</section>

<script src="<c:url value="/js/admin/configs/configs.controller.js"/>"></script>
<script lang="javascript">
$(function () {
    // initialize select2 elements
    $('.select2').select2({allowClear:true});
});

$(document).ready(function() {
    
    var settings = {
        apis: {
            restful: { url: '<c:url value="/api/admin/configurations/"/>' }
        },
        validation: {
            'name': { required: true, minlength: 3, maxlength: 60 },
            'context': { maxlength: 60 },
            'value': { required: false }
        }
    };
    
    $.Casper.crud(settings).bind(ConfigurationCrudController).init();
});
</script>

<script id="template-crud-tbl" type="text/html">
<table id="tbl-page-records" class="table table-hover table-striped">
<tbody>
<tr>
    <th style="width: 10px">ID</th>
    <th><fmt:message key="admin.configs.lbl.context"/></th>
    <th><fmt:message key="admin.configs.lbl.name"/></th>
    <th style="width: 70px"><fmt:message key="lbl.actions"/></th>
</tr>
{{#data.content}}
    <tr>
        <td>{{id}}</td>
        <td>{{context}}</td>
        <td>{{name}}</td>
        <td>
            <casper:CrudRecordActions />
        </td>
    </tr>
{{/data.content}}

{{^data.content}}
    <tr><td colspan="4" class="text-center"><fmt:message key="lbl.no.records.found"/></td></tr>
{{/data.content}}
</tbody>
</table>
</script>