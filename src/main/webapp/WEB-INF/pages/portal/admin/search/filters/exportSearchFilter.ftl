<#import "../../../../fieldMacro.ftl" as formMacro />
<h4><strong><@spring.message "export.search.result.page.criteria"/></strong></h4>
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormTextInput "exportFilter.name" "exportFilter.name"/>
        </div>
        <div class="col-md-6">
            <@formMacro.rtcFormSingleSelect "export.lable.exportClass" "exportFilter.exportClass" exportTypes "" "" "export.exportClass." {"" : "All"}/>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
</div>
<br>