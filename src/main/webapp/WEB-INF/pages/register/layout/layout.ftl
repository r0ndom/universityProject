<#macro layout>
<html>
    <#import "/spring.ftl" as spring/>
    <#import "../../fieldMacro.ftl" as formMacro />
<head>
    <@formMacro.rtcIncludeLink />
    <@formMacro.rtcIncludeScript/>
</head>

<body>
    <div class="container" style="margin-bottom: 60px">
        <div class="row col-md-offset-1">
            <div class="center-block">
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

