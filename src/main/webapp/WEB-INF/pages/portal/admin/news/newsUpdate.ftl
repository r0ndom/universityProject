<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<@formMacro.rtcForm "newsForm" "/admin/news/edit" "news.edit" "${validationRules}" "multipart/form-data">
    <@spring.formHiddenInput "news.code" />
    <@spring.formHiddenInput "news.id" />
    <@spring.formHiddenInput "news.status" />
    <#include "newsForm.ftl" />
    <@formMacro.rtcSubmit "Save" "Cancel" "/admin/news/${news.code}"/>
</@formMacro.rtcForm>
</@layout.layout>
