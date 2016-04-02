<#macro layout>
<html>

    <#import "/spring.ftl" as spring/>
    <#import "../../../fieldMacro.ftl" as formMacro />
<head>

    <@formMacro.rtcIncludeLink />
    <@formMacro.rtcIncludeScript/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans&subset=latin,cyrillic-ext' rel='stylesheet' type='text/css'>
</head>

<body>
<div id="header" class="navbar navbar-inverse navbar-fixed-top"
     role="navigation">
    <#include "header.ftl" />
</div>

<div class="container-fixed">
    <div class="container-fluid" style="margin-bottom: 60px; margin-left: 5%; margin-right: 5%">
        <div class="row-fluid">
            <div id="menu" class="col-sm-3 col-md-2 sidebar">
                <#include "menu.ftl" />
            </div>
            <div id="content" class="col-sm-offset-3 col-md-offset-2 main">
                <#nested>
            </div>
        </div>
    </div>
</div>

<div id="footer" class="container">

    <#include "footer.ftl" />
</div>
</body>

</html>
</#macro>

