<!DOCTYPE html>
<html lang="en">
<head>
<sitemesh:write property="head" />
</head>
<body>
    <%@ include file="/WEB-INF/views/fragments/header.jsp"%>
    
    <main id="page-wrapper5">
    <div class="container-fluid">
        <sitemesh:write property="body" />
    </div>
    </main>
    
    <%@ include file="/WEB-INF/views/fragments/footer.jsp"%>
</body>
</html>