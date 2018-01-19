<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="casper" %>

<casper:Breadcrumbs>
    <li class=""><a href="<c:url value="/security"/>"><fmt:message key="security.title"/></a></li>
    <li class="active"><fmt:message key="security.users.title"/></li>
</casper:Breadcrumbs>

<section class="content">

    <div class="col-md-6">
        <casper:CrudRecordsPanel title="security.users.title" />
    </div>
    
    <div class="col-md-6">
        <casper:CrudRecordPanel title="security.user.title">
            <input type="hidden" class="form-control" id="id" name="id" />
            <input type="hidden" class="form-control" id="lockVersion" name="lockVersion" />
            <input type="hidden" class="form-control" id="audit" name="audit[id]" />
            
            <div class="box-body">
                <div class="form-group">
                    <label for="emailAddress" class="col-sm-3 control-label"><fmt:message key="security.users.lbl.emailAddress"/></label>
                    <div class="col-sm-9 col-lg-6">
                        <input type="text" class="form-control" id="emailAddress" name="emailAddress" placeholder="<fmt:message key="security.users.lbl.emailAddress"/>" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="employeeNumber" class="col-sm-3 control-label"><fmt:message key="security.users.lbl.employeeNumber"/></label>
                    <div class="col-sm-9 col-lg-3">
                        <input type="text" readonly="readonly" class="form-control" id="employeeNumber" name="employeeNumber" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="enabled" class="col-sm-3 control-label"><fmt:message key="security.users.lbl.enabled"/></label>
                    <div class="col-sm-9 col-lg-2">
                        <input type="checkbox" id="enabled" name="enabled" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="expired" class="col-sm-3 control-label"><fmt:message key="security.users.lbl.expired"/></label>
                    <div class="col-sm-9 col-lg-2">
                        <input type="checkbox" id="expired" name="expired" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="locked" class="col-sm-3 control-label"><fmt:message key="security.users.lbl.locked"/></label>
                    <div class="col-sm-9 col-lg-2">
                        <input type="checkbox" id="locked" name="locked" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="box-roles" class="col-sm-3 control-label"><fmt:message key="security.users.lbl.roles"/></label>
                    <div id="box-roles" class="col-sm-9 col-lg-9"></div>
                </div>
            </div>
        </casper:CrudRecordPanel>
    </div>
    
</section>

<script src="<c:url value="/js/security/users/users.controller.js"/>"></script>
<script lang="javascript">
$(document).ready(function() {
    var settings = {
        apis: {
            restful: { url: '<c:url value="/api/security/users/"/>' }
        },
        validation: {
            'emailAddress': { required: true, email: true, maxlength: 140 }
        }
    };
    
    $.Casper.crud(settings).bind(UserCrudController).init();
});
</script>

<script id="template-crud-tbl" type="text/html">
<table id="tbl-crud-records" class="table table-hover table-striped">
<tbody>
<tr>
    <th style="width: 10px">ID</th>
    <th><fmt:message key="security.users.lbl.emailAddress"/></th>
    <th><fmt:message key="security.users.lbl.employeeNumber"/></th>
    <th style="width: 70px"><fmt:message key="lbl.actions"/></th>
</tr>
{{#data.content}}
    <tr>
        <td>{{id}}</td>
        <td>{{emailAddress}}</td>
        <td>{{employeeNumber}}</td>
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

<script id="template-edit-role" type="text/html">
<div id="box-page-list" class="box">
    <div class="box-header">
        <label for="addRole"><fmt:message key="security.users.lbl.addRole"/></label>
        <input id="addRole" class="ui-autocomplete-input form-control" placeholder="<fmt:message key="security.users.lbl.addRole.placeholder"/>" />
    </div>
    <div class="box-body no-padding">
        <table id="tbl-list-roles" class="table table-hover table-striped">
        <tbody>
        <tr>
            <th style="width: 10px">ID</th>
            <th><fmt:message key="security.roles.lbl.name"/></th>
            <th style="width: 70px"><fmt:message key="lbl.actions"/></th>
        </tr>
        {{#roles}}
            {{> template-edit-role-item}}
        {{/roles}}
        </tbody>
        </table>
    </div>
</div>
</script>

<script id="template-edit-role-item" type="text/html">
<tr>
    <td><input type="hidden" name="roles[][id]" value="{{id}}" />{{id}}</td>
    <td>{{name}}</td>
    <td>
        <a href="#" data-id="{{id}}" name="btn-delete-role" id="btn-delete-role-{{id}}">
        <i class="fa fa-trash-o pull-right text-red"></i>
        </a>            
    </td>
</tr>
</script>

<script id="template-view-role" type="text/html">
<div id="box-page-list" class="box">
    <div class="box-body">
        <table id="tbl-list-roles" class="table table-hover table-striped">
        <tbody>
        <tr>
            <th style="width: 10px">ID</th>
            <th><fmt:message key="security.roles.lbl.name"/></th>
        </tr>
        {{#roles}}
            <tr>
                <td><input type="hidden" name="roles[][id]" value="{{id}}" />{{id}}</td>
                <td>{{name}}</td>
            </tr>
        {{/roles}}

        {{^roles}}
            <tr><td colspan="4" class="text-center"><fmt:message key="lbl.no.records.found"/></td></tr>
        {{/roles}}
        </tbody>
        </table>
    </div>
</div>
</script>