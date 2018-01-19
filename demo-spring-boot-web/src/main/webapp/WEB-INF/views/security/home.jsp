<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section class="content">

<div class="row">
    <div class="col-md-12">
      <div class="box box-primary">
        <div class="box-header">
          <i class="fa fa-lock"></i>
          <h3 class="box-title"><fmt:message key="security.title"/></h3>
        </div>
        
        <div class="box-body pad table-responsive">
            <p><fmt:message key="security.subtitle"/></p>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/security/roles" />">
                <i class="fa fa-id-badge"></i> <fmt:message key="security.item.roles"/>
            </a>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/security/permissions" />">
                <i class="fa fa-key"></i> <fmt:message key="security.item.permissions"/>
            </a>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/security/users" />">
                <i class="fa fa-users"></i> <fmt:message key="security.item.users"/>
            </a>
        </div>
        <!-- /.box -->
      </div>
    </div>
    <!-- /.col -->
</div>

</section>