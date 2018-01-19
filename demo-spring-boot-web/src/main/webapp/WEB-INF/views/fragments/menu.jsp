<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<aside class="main-sidebar">

  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">

    <!-- Sidebar user panel (optional) -->
    <div class="user-panel">
        <div class="pull-left">
            <img src="<c:url value="/img/hpe_logo.png"/>" alt="HPE" class="hidden-xs" />
        </div>
    </div>

    <!-- search form (Optional) -->
    <!--
    <form action="#" method="get" class="sidebar-form">
      <div class="input-group">
        <input type="text" name="q" class="form-control" placeholder="Search..." />
        <span class="input-group-btn">
          <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
        </span>
      </div>
    </form>
    -->
    <!-- /.search form -->

    <!-- Sidebar Menu -->
    <ul class="sidebar-menu">
      <li class="header"><fmt:message key="app.menu.title"/></li>
      <li class="active"><a href="<c:url value="/workspace" />"><i class="fa fa-tv"></i> <span><fmt:message key="app.menu.workspace"/></span></a></li>
      
      <!--
      <li><a href="<c:url value="/dashboard" />"><i class="fa fa-bar-chart"></i> <span><fmt:message key="app.menu.dashboard"/></span></a></li>
      <li><a href="<c:url value="/notifications" />"><i class="fa fa-newspaper-o"></i> <span><fmt:message key="app.menu.notifications"/></span></a></li>
      -->
      
      <!-- ADMIN -->
      <li class="treeview">
        <a href="#"><i class="fa fa-cogs"></i> <span><fmt:message key="app.menu.admin"/></span>
          <span class="pull-right-container">
            <i class="fa fa-angle-left pull-right"></i>
          </span>
        </a>
        <ul class="treeview-menu">
          <li><a href="<c:url value="/admin/configuration" />"><fmt:message key="admin.item.configs"/></a></li>
        </ul>
      </li>
      <!-- MANAGER -->
      <li class="treeview">
        <a href="#"><i class="fa fa-file-text-o"></i> <span><fmt:message key="app.menu.manager"/></span>
          <span class="pull-right-container">
            <i class="fa fa-angle-left pull-right"></i>
          </span>
        </a>
        <ul class="treeview-menu">
          <li><a href="<c:url value="/manager/application" />"><i class="fa fa-desktop"></i><span><fmt:message key="manager.item.apps"/></span></a></li>
        </ul>
      </li>
      <!-- SECURITY -->
      <li class="treeview">
        <a href="#"><i class="fa fa-lock"></i> <span><fmt:message key="app.menu.security"/></span>
          <span class="pull-right-container">
            <i class="fa fa-angle-left pull-right"></i>
          </span>
        </a>
        <ul class="treeview-menu">
          <li><a href="<c:url value="/security/roles" />"><i class="fa fa-id-badge"></i><span><fmt:message key="security.item.roles"/></span></a></li>
          <li><a href="<c:url value="/security/permissions" />"><i class="fa fa-key"></i><span><fmt:message key="security.item.permissions"/></span></a></li>
          <li><a href="<c:url value="/security/users" />"><i class="fa fa-users"></i><span><fmt:message key="security.item.users"/></span></a></li>
        </ul>        
      </li>
      <!-- DEMO -->
      <sec:authorize access="hasAuthority('CAC_VIEW_DEMO_PAGE')">
      <li class="treeview">
        <a href="#"><i class="fa fa-flask"></i> <span><fmt:message key="app.menu.demo"/></span>
          <span class="pull-right-container">
            <i class="fa fa-angle-left pull-right"></i>
          </span>
        </a>
        <ul class="treeview-menu">
          <li><a href="<c:url value="/demo/security" />"><i class="fa fa-lock"></i><span><fmt:message key="demo.item.security"/></span></a></li>
        </ul>
      </li>
      </sec:authorize>
    </ul>
    <!-- /.sidebar-menu -->
  </section>
  <!-- /.sidebar -->
</aside>