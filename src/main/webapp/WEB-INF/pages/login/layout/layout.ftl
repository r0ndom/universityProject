<#macro layout>
<html>
<head>
    <link href="<@spring.url'/resources/css/bootstrap.min.css'/>"  rel="stylesheet"/>
    <link href="<@spring.url'/resources/css/application.css'/>"  rel="stylesheet"/>
    <link href="<@spring.url'/resources/css/wink/wink.css'/>"  rel="stylesheet"/>
</head>

<body>
    <div class="container">
        <div class="row">
            <#nested>
        </div>
    </div>
    <div id="footer" class="container">
        <#include "footer.ftl" />
    </div>
</body>

<script src="<@spring.url'/resources/js/jquery/jquery-1.11.1.min.js'/>"></script>
<script src="<@spring.url'/resources/js/bootstrap.min.js'/>"></script>
</html>
</#macro>




