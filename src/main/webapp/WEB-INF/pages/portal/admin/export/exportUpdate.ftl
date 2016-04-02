<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
    <@formMacro.rtcForm "exportForm" "/admin/export/updateExport" "export.edit" "${validationRules}">
        <@spring.formHiddenInput "export.id" />
        <@spring.formHiddenInput "export.code" />
        <@spring.formHiddenInput "export.createdDate" />
        <#include "exportForm.ftl" />
        <div class="row-fluid span12" style="margin-left: 1px">
            <@formMacro.rtcSubmitDoOrCancel "action.update" "/admin/export/update/${export.code}" "action.cancel"
            "/admin/search/${menuItem}"/>
        </div>
    </@formMacro.rtcForm>
</@layout.layout>
