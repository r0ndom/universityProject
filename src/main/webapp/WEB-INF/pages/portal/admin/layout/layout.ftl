<#macro layout>
<html>

    <#import "/spring.ftl" as spring/>
    <#import "../../../fieldMacro.ftl" as formMacro />
<head>

    <@formMacro.rtcIncludeLink />
    <@formMacro.rtcIncludeScript/>
</head>

<body>
<div id="header" class="navbar navbar-inverse navbar-fixed-top"
     role="navigation">
    <#include "header.ftl" />
</div>

<div class="container-fluid" style="margin-bottom: 60px">
    <div class="row-fluid">
            <div id="menu" class="col-sm-3 col-md-2 sidebar">
                <#include "menu.ftl" />
            </div>
            <div id="content" class="col-sm-offset-3 col-md-offset-2 main">
                <#nested>
            </div>
    </div>
</div>
<div id="footer" class="container">

    <#include "footer.ftl" />
</div>
</body>

</html>
</#macro>

