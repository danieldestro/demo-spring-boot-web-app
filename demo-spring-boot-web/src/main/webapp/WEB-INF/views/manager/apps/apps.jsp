<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="casper" tagdir="/WEB-INF/tags" %>

<casper:Breadcrumbs>
    <li class=""><a href="<c:url value="/manager"/>"><fmt:message key="manager.title"/></a></li>
    <li class="active"><fmt:message key="manager.apps.title"/></li>
</casper:Breadcrumbs>

<section class="content">

    <div class="col-md-6">
        <casper:CrudRecordsPanel title="manager.apps.title" />
    </div>

    <div class="col-md-6">
        <casper:CrudRecordPanel title="manager.app.title">
            <!-- Application Record Form Fields -->
            <input type="hidden" class="form-control" id="id" name="id">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-3 control-label"><fmt:message key="manager.apps.lbl.name"/></label>
                    <div class="col-sm-9 col-lg-6">
                        <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="manager.apps.lbl.name"/>" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-3 control-label"><fmt:message key="manager.apps.lbl.description"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="description" name="description" placeholder="<fmt:message key="manager.apps.lbl.description"/>" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-3 control-label"><fmt:message key="manager.apps.lbl.url"/></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="url" name="url" placeholder="<fmt:message key="manager.apps.placeholder.url"/>" />
                    </div>
                </div>
            </div>
        </casper:CrudRecordPanel>
    </div>
</section>

<script src="<c:url value="/js/manager/apps/apps.controller.js"/>"></script>
<script lang="javascript">
$(document).ready(function() {
    var settings = {
        apis: {
            /*
            list: { url: '<c:url value="/api/manager/applications/"/>' },
            save: { url: '<c:url value="/api/manager/applications/"/>' },
            update: { url: '<c:url value="/api/manager/applications/"/>' },
            load: { url: '<c:url value="/api/manager/applications/"/>' },
            'delete': { url: '<c:url value="/api/manager/applications/"/>' }
            */
            restful: { url: '<c:url value="/api/manager/applications/"/>' }
        },
        validation: {
            'name': { required: true, minlength: 3, maxlength: 60 },
            'description': { maxlength: 255 },
            'url': { required: true, url: true }
        }
    };
    
    $.Casper.crud(settings).bind(ApplicationCrudController).init()
    //.alert('info','hello')
    //.dialog('This is an important information!')
    ;
    /*
    $.Casper.toaster('info','Cool');
    $.Casper.toaster('warn','Oh uau','Oh owl');
    $.Casper.toaster('success','Happy Days');
    $.Casper.toaster('error','oops','Feckit');
    */
});
</script>

<script id="template-crud-tbl" type="text/html">
<table id="tbl-page-records" class="table table-hover table-striped">
<tbody>
<tr>
    <th style="width: 10px">ID</th>
    <th><fmt:message key="manager.apps.lbl.name"/></th>
    <th><fmt:message key="manager.apps.lbl.link"/></th>
    <th style="width: 70px"><fmt:message key="lbl.actions"/></th>
</tr>
{{#data.content}}
    <tr>
        <td>{{id}}</td>
        <td>{{name}}</td>
        <td><a href="{{url}}" target="_blank"><i class="fa fa-link text-acqua"></i></a></td>
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