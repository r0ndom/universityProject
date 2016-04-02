<#ftl strip_whitespace=true>
<#if errorTitle == 'Critical Error'>
    <div class="panel panel-danger">
<#else>
    <div class="panel panel-warning">
</#if>
    <div class="panel-heading">
        <h3 class="panel-title">${errorTitle}</h3>
    </div>
    <div class="panel-body">
        ${errorMessage}
    </div>
</div>
<div style="visibility: hidden">
    <p>${errorCause}</p>
</div>
