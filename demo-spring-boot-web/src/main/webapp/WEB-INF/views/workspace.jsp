<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<section class="content">

<div class="row">
    <div class="col-md-12">
      <div class="box box-primary">
        <div class="box-header">
          <i class="fa fa-tv"></i>
          <h3 class="box-title"><fmt:message key="workspace.title"/></h3>
        </div>
        <div class="box-body pad table-responsive">
            <p><fmt:message key="workspace.subtitle"/></p>
            
            <!--
            <a class="btn btn-app btn-lg" href="<c:url value="/dashboard" />">
                <i class="fa fa-bar-chart"></i> <fmt:message key="workspace.item.dashboard"/>
            </a>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/notifications" />">
                <i class="fa fa-newspaper-o"></i> <fmt:message key="workspace.item.notifications"/>
            </a>
            -->
            
            <a class="btn btn-app btn-lg" href="<c:url value="/admin" />">
                <i class="fa fa-cogs"></i> <fmt:message key="workspace.item.admin"/>
            </a>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/manager" />">
                <i class="fa fa-file-text-o"></i> <fmt:message key="workspace.item.manager"/>
            </a>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/security" />">
                <i class="fa fa-lock"></i> <fmt:message key="workspace.item.security"/>
            </a>
            
            <sec:authorize access="hasAuthority('CAC_VIEW_DEMO_PAGE')">
            <a class="btn btn-app btn-lg" href="<c:url value="/demo" />">
                <i class="fa fa-flask"></i> <fmt:message key="workspace.item.demo"/>
            </a>
            </sec:authorize>
        </div>
        <!-- /.box -->
      </div>
    </div>
    <!-- /.col -->
</div>

</section>