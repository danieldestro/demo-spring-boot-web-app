<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="casper" %>

<!DOCTYPE html>
<html>
<head>
    <sec:csrfMetaTags />
    <meta name="_casper_context" content="<c:url value="/"/>" />
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="app.title"/></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>" />
    <!-- Font Awesome 4.7.0 -->
    <link rel="stylesheet" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" />
    <!-- Ionicons 2.0.1 -->
    <link rel="stylesheet" href="<c:url value="/webjars/ionicons/2.0.1/css/ionicons.min.css"/>" />
    <!-- Theme style -->
    <link rel="stylesheet" href="<c:url value="/webjars/adminlte/2.3.11/dist/css/AdminLTE.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/webjars/adminlte/2.3.11/dist/css/skins/skin-green-light.min.css"/>" />
    <!-- Flags Icons http://flag-icon-css.lip.is/ -->
    <link rel="stylesheet" href="<c:url value="/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/plugins/waitMe/waitMe.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/plugins/select2/select2.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/plugins/toast/toast.custom.css"/>" />
    <link rel="stylesheet" href="<c:url value="/css/casper.css"/>" />
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- REQUIRED JS SCRIPTS -->
    <script src="<c:url value="/webjars/jquery/2.2.3/jquery.min.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/webjars/adminlte/2.3.11/dist/js/app.min.js"/>"></script>
    <!-- https://github.com/jonnyreeves/jquery-Mustache -->
    <script src="<c:url value="/plugins/mustache/mustache.jquery.js"/>"></script>
    <script src="<c:url value="/webjars/mustache/2.3.0/mustache.js"/>"></script>
    <script src="<c:url value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
    <script src="<c:url value="/plugins/waitMe/waitMe.min.js"/>"></script>
    <script src="<c:url value="/plugins/toast/toast.min.js"/>"></script>
    <script src="<c:url value="/plugins/jQueryUI/jquery-ui.min.js"/>"></script>
    <!--
    <script src="<c:url value="/plugins/jsonify/jquery.jsonify-0.3.2.min.js"/>"></script>
    <script src="<c:url value="/plugins/serializejson/jquery.serializejson.js"/>"></script>
    <script src="<c:url value="/plugins/jsForm/jquery.jsForm.min.js"/>"></script>
    -->
    <script src="<c:url value="/plugins/transForm/transForm.min.js"/>"></script>
    <script src="<c:url value="/plugins/jquery-serialize-object/jquery.serialize-object.min.js"/>"></script>
    <script src="<c:url value="/webjars/jquery-validation/1.17.0/dist/jquery.validate.js"/>"></script>
    
    <script src="<c:url value="/plugins/casper/casper-jquery.min.js"/>"></script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <tiles:insertAttribute name="header" />
  
  <!-- Left side column. contains the logo and sidebar -->
  <tiles:insertAttribute name="menu" />

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
  
    <section class="content-alert-message">
        <div id="box-alert-message"></div>
    </section>
    
    <!-- Main content -->
    <tiles:insertAttribute name="body" />
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
  <tiles:insertAttribute name="footer" />

  <!-- Control Sidebar -->
  <tiles:insertAttribute name="controlbar" />
</div>
<!-- ./wrapper -->

<!-- alert-banners -->
<casper:AlertBanner type="error" title="Error" />
<casper:AlertBanner type="warn" title="Warn" />
<casper:AlertBanner type="success" title="Success" />
<casper:AlertBanner type="info" title="Information" />
<!-- ./alert-banners -->

<script id="adminlte-validation-styles" type="text/javascript">
// Adding default settings for AdminLTE + jQuery Validation
$.validator.setDefaults({
    highlight: function(element) {
        $(element).closest('.form-group').addClass('has-error');
        $(element).closest('.form-group').removeClass('has-success has-feedback').addClass('has-error has-feedback');
        $(element).closest('.form-group').find('i.fa').remove();
        $(element).closest('.form-group').find(".control-label").append(' <i class="fa fa-times-circle-o"></i>');
    },
    unhighlight: function(element) {
        $(element).closest('.form-group').removeClass('has-error');
        $(element).closest('.form-group').find('i.fa').remove();
        // disabling the success validation feedback
        //$(element).closest('.form-group').addClass('has-success');
        //$(element).closest('.form-group').find(".control-label").append(' <i class="fa fa-check"></i>');
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function(error, element) {
        if (element.parent('.input-group').length) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }
});
</script>

</body>
</html>