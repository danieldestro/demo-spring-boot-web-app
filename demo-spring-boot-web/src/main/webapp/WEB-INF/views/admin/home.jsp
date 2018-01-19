<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section class="content">

<div class="row">
    <div class="col-md-12">
      <div class="box box-primary">
        <div class="box-header">
          <i class="fa fa-cogs"></i>
          <h3 class="box-title"><fmt:message key="admin.title"/></h3>
        </div>
        
        <div class="box-body pad table-responsive">
            <p><fmt:message key="admin.subtitle"/></p>
            
            <a class="btn btn-app btn-lg" href="<c:url value="/admin/configuration" />">
                <i class="fa fa fa-cog"></i> <fmt:message key="admin.item.configs"/>
            </a>
        </div>
        <!-- /.box -->
      </div>
    </div>
    <!-- /.col -->
</div>

</section>