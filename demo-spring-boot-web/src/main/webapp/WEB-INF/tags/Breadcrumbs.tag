<%@ tag %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="style" required="false" type="String" %>

<section class="content-header">
    <!--
    <h1 id="title"><fmt:message key="manager.apps.title" /></h1>
    -->
    <div class="col-md-12 pull-left">
        <ol class="breadcrumb pull-left">
            <li><a href="<c:url value="/workspace" />"><i class="fa fa-desktop"></i> CAC</a></li>
<jsp:doBody/>
        </ol>
    </div>
</section>