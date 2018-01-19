<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<footer class="main-footer">
  <!-- To the right -->
  <div class="pull-right hidden-xs">
    <span><fmt:message key="app.version.label"/></span>: <spring:eval expression="@appConfigProperties.appVersionNumber" /> - 
    <span><fmt:message key="app.build.number.label"/></span>: <spring:eval expression="@appConfigProperties.appBuildNumber" /> 
    <!--
    <fmt:message key="app.version.number"/>
    <spring:message code="app.name.symbol"/>
    -->
  </div>
  <!-- Default to the left -->
  <strong>
  <span><fmt:message key="app.footer.copyright"/></span> 
  <a href="http://www.demo.com" target="_blank"><fmt:message key="company.name"/></a>
  </strong>
</footer>