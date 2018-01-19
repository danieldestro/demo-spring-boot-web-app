<%@ tag %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="casper" tagdir="/WEB-INF/tags" %>

<%@ attribute name="title" required="true" type="String" %>

<!-- Record Panel -->
<div id="box-page-record" class="box box-primary collapse">
    <div class="box-header with-border">
        <h3 class="box-title"><fmt:message key="${title}"/></h3>
        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
        </div>
    </div>
    <div id="box-page-record-form" class="box-header with-border">
        <!-- form start -->
        <form class="form-horizontal" id="frm-page-record">
<jsp:doBody/>
            <div class="box-footer">
                <casper:ActionButton id="btn-page-cancel" type="cancel"/>
                <casper:ActionButton id="btn-page-save" type="save" position="right"/>
            </div>
        </form>
    </div>
</div>
<!-- /.Record Panel -->