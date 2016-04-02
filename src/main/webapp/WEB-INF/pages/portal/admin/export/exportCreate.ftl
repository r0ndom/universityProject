<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>

<@formMacro.rtcForm "exportForm" "/admin/export/insertExport" "export.create.page.header" "${validationRules}">
    <#include "exportForm.ftl" />
    <@formMacro.rtcSubmit "Create" "Cancel" "/admin/search/${menuItem}"/>
</@formMacro.rtcForm>

</@layout.layout>

<script type="text/javascript">
    function CheckNullExportFields(userCode) {
        $("#userCode").val(userCode);
        $('#removeUserModal').modal('show')
    }
</script>
