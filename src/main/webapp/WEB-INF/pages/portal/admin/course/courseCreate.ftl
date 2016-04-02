<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
    <@formMacro.rtcForm "courseForm" "/admin/course/save" "create.message" "${validationRules}">
        <#include "courseForm.ftl" />
        <@formMacro.rtcSubmit "Create" "Cancel" "/admin/search/${menuItem}"/>
    </@formMacro.rtcForm>
</@layout.layout>

