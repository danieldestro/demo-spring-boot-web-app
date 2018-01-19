<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<header class="main-header">

  <!-- Logo -->
  <a href="<c:url value="/workspace" />" class="logo">
    <!-- mini logo for sidebar mini 50x50 pixels -->
    <span class="logo-mini"><b><fmt:message key="app.name.symbol"/></b></span>
    <!-- logo for regular state and mobile devices -->
    <span class="logo-lg"><fmt:message key="app.name"/></span>
  </a>

  <!-- Header Navbar -->
  <nav class="navbar navbar-static-top" role="navigation">
    <!-- Sidebar toggle button-->
    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
      <span class="sr-only">Toggle navigation</span>
    </a>
    <!-- Navbar Right Menu -->
    <div class="navbar-custom-menu">
      <ul class="nav navbar-nav">
        <!-- Messages: style can be found in dropdown.less-->
        
        <!--
        <li class="dropdown messages-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <i class="flag-icon flag-icon-<fmt:message key="app.lang.country.code"/>"></i>
            </a>
            <ul class="dropdown-menu">
                <li class="header"><fmt:message key="app.lang.choose"/></li>
                <li><a href="?lang=en"><i class="flag-icon flag-icon-us"></i> English</a></li>
                <li><a href="?lang=es"><i class="flag-icon flag-icon-es"></i> Español</a></li>
                <li><a href="?lang=fr"><i class="flag-icon flag-icon-fr"></i> Français</a></li>
                <li><a href="?lang=pt"><i class="flag-icon flag-icon-br"></i> Português</a></li>
            </ul>
        </li>
        -->
        
        <!-- HIDDEN (by css) -->
        <li class="dropdown messages-menu hidden">
          <!-- Menu toggle button -->
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <i class="fa fa-envelope-o"></i>
            <span class="label label-success">4</span>
          </a>
          <ul class="dropdown-menu">
            <li class="header">You have 4 messages</li>
            <li>
              <!-- inner menu: contains the messages -->
              <ul class="menu">
                <li><!-- start message -->
                  <a href="#">
                    <div class="pull-left">
                      <!-- User Image -->
                      <img src="<c:url value="/dist/img/user2-160x160.jpg"/>" class="img-circle" alt="User Image" />
                    </div>
                    <!-- Message title and timestamp -->
                    <h4>
                      Support Team
                      <small><i class="fa fa-clock-o"></i> 5 mins</small>
                    </h4>
                    <!-- The message -->
                    <p>Why not buy a new awesome theme?</p>
                  </a>
                </li>
                <!-- end message -->
              </ul>
              <!-- /.menu -->
            </li>
            <li class="footer"><a href="#">See All Messages</a></li>
          </ul>
        </li>
        <!-- /.messages-menu -->

        <!-- HIDDEN (by css) -->
        <!-- Notifications Menu -->
        <li class="dropdown notifications-menu hidden">
          <!-- Menu toggle button -->
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <i class="fa fa-bell-o"></i>
            <span class="label label-warning">10</span>
          </a>
          <ul class="dropdown-menu">
            <li class="header">You have 10 notifications</li>
            <li>
              <!-- Inner Menu: contains the notifications -->
              <ul class="menu">
                <li><!-- start notification -->
                  <a href="#">
                    <i class="fa fa-users text-aqua"></i> 5 new members joined today
                  </a>
                </li>
                <!-- end notification -->
              </ul>
            </li>
            <li class="footer"><a href="#">View all</a></li>
          </ul>
        </li>
        
        <!-- HIDDEN (by css) -->
        <!-- Tasks Menu -->
        <li class="dropdown tasks-menu hidden">
          <!-- Menu Toggle Button -->
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <i class="fa fa-flag-o"></i>
            <span class="label label-danger">9</span>
          </a>
          <ul class="dropdown-menu">
            <li class="header">You have 9 tasks</li>
            <li>
              <!-- Inner menu: contains the tasks -->
              <ul class="menu">
                <li><!-- Task item -->
                  <a href="#">
                    <!-- Task title and progress text -->
                    <h3>
                      Design some buttons
                      <small class="pull-right">20%</small>
                    </h3>
                    <!-- The progress bar -->
                    <div class="progress xs">
                      <!-- Change the css width attribute to simulate progress -->
                      <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                        <span class="sr-only">20% Complete</span>
                      </div>
                    </div>
                  </a>
                </li>
                <!-- end task item -->
              </ul>
            </li>
            <li class="footer">
              <a href="#">View all tasks</a>
            </li>
          </ul>
        </li>
        
        <!-- User Account Menu -->
        <li class="dropdown user user-menu">
          <!-- Menu Toggle Button -->
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <i class="fa fa-user-circle-o"></i>
            <span class="hidden-xs"><fmt:message key="app.header.myaccount"/></span>
          </a>
          <ul class="dropdown-menu">
            <!-- The user image in the menu -->
            <!--
            <li class="user-header">
              <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
              <p>
                <sec:authentication property="principal.emailAddress" /> <br/>
                <small><fmt:message key="app.header.empnum"/>: <sec:authentication property="principal.username" /></small>
              </p>
            </li>
            -->
            <!-- Menu Body -->
            <li class="user-header-2">
              <div class="row">
                  <p>
                    <sec:authentication property="principal.emailAddress" /> <br/>
                    <small><fmt:message key="app.header.empnum"/>: <sec:authentication property="principal.username" /></small>
                  </p>
              </div>
            </li>
            <!--
            <li class="user-body">
              <div class="row">
                <div class="col-xs-4 text-center">
                  <a href="#">Followers</a>
                </div>
                <div class="col-xs-4 text-center">
                  <a href="#">Sales</a>
                </div>
                <div class="col-xs-4 text-center">
                  <a href="#">Friends</a>
                </div>
              </div>
            </li>
            -->
            <!-- Menu Footer-->
            <li class="user-footer">
              <!--
              <div class="pull-left">
                <a href="#" class="btn btn-default btn-flat"><fmt:message key="app.header.profile"/></a>
              </div>
              -->
              <div class="pull-right">
                <a href="<c:url value="/logout" />" class="btn btn-default btn-flat"><fmt:message key="app.header.signout"/></a>
              </div>
            </li>
          </ul>
        </li>
        
        <!-- HIDDEN (by css) -->
        <!-- Control Sidebar Toggle Button -->
        <li class="hidden">
          <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
        </li>
      </ul>
    </div>
  </nav>
</header>