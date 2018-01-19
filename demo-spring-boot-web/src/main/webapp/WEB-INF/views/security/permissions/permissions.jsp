<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="casper" %>

<casper:Breadcrumbs>
    <li class=""><a href="<c:url value="/security"/>"><fmt:message key="security.title"/></a></li>
    <li class="active"><fmt:message key="security.permissions.title"/></li>
</casper:Breadcrumbs>

<section class="content">

    <div class="col-md-6">
        <casper:CrudRecordsPanel title="security.permissions.title" />
    </div>
    
    <div class="col-md-6">
        <casper:CrudRecordPanel title="security.permission.title">
            <input type="hidden" class="form-control" id="id" name="id">
            <input type="hidden" class="form-control" id="lockVersion" name="lockVersion">
            <input type="hidden" class="form-control" id="audit" name="audit[id]" />
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-3 control-label"><fmt:message key="security.permissions.lbl.name"/></label>
                    <div class="col-sm-9 col-lg-6">
                        <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="security.permissions.lbl.name"/>" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-3 control-label"><fmt:message key="security.permissions.lbl.description"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="description" name="description" placeholder="<fmt:message key="security.permissions.lbl.description"/>" />
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
            restful: { url: '<c:url value="/api/security/permissions/"/>' }
        },
        validation: {
            'name': { required: true, minlength: 3, maxlength: 60 },
            'description': { required: true, minlength: 3, maxlength: 255 }
        }
    };
    
    $.Casper.crud(settings).bind(CasperVoidController).init();
});
</script>

<script id="template-crud-tbl" type="text/html">
<table id="tbl-crud-records" class="table table-hover table-striped">
<tbody>
<tr>
    <th style="width: 10px">ID</th>
    <th><fmt:message key="security.permissions.lbl.name"/></th>
    <th style="width: 70px"><fmt:message key="lbl.actions"/></th>
</tr>
{{#data.content}}
    <tr>
        <td>{{id}}</td>
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