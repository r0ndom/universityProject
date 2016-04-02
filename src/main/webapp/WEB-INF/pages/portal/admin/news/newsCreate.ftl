<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
    <@formMacro.rtcForm "newsForm" "/admin/news/save" "news.create" "${validationRules}">
        <#include "newsForm.ftl" />
        <@formMacro.rtcSubmit "Create" "Cancel" "/admin/search/${menuItem}"/>
    </@formMacro.rtcForm>
</@layout.layout>
