<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section class="content-header">
  <h1 id="title"><fmt:message key="home.title"/></h1>
  
  <ol class="breadcrumb">
    <li><a href="<c:url value="/" />"><i class="fa fa-desktop"></i> CAC</a></li>
    <li class="active">Home</li>
  </ol>
</section>

<section class="content">

<p><a href="<c:url value="/workspace" />"><fmt:message key="home.message"/></a></p>

</section>