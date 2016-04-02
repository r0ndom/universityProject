<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<h4><strong><@spring.message "export.details"/></strong></h4>
<style>
    label {
        float: left;
        width: 13em;
        margin-right: 1em;
        text-align: right;
        font-size: 10pt;
        margin-bottom: 10px;
    }
</style>
<div class="row">
    <div class="col-md-6">
        <div class="col-md-12">
            <@formMacro.rtcFormLabelOut "export.lable.name" "${export.name}"/>
        </div>
        <div class="col-md-12">
            <@formMacro.rtcFormLabelOut "export.lable.exportClass" "${export.exportClass}"/>
        </div>
    </div>
    <div class="col-md-6">
        <div class="col-md-12">
            <@formMacro.rtcFormLabelOut "export.lable.exportFormat" "${export.exportFormat}"/>
        </div>
    </div>
</div>
&NonBreakingSpace;
<hr>
<div class="row">
    <div class="col-md-6">
        <p style="margin-left: 6em"><b><@spring.message "export.lable.fields"/></b></p>
        <#if export.fields??>
            <#list export.fields as field>
                <label></label>

                <p>&nbsp${field}</p>
            </#list>
        </#if>
    </div>
</div>
<hr>
<div class="row">
<@formMacro.rtcSubmitDoOrCancel "action.edit" "/admin/export/update/${export.code}" "action.cancel" "/admin/search/${menuItem}"/>
</div>
</@layout.layout>
