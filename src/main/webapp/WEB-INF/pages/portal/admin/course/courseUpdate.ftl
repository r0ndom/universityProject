<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
    <@formMacro.rtcForm "courseForm" "/admin/course/update" "update.message" "${validationRules}" "multipart/form-data">
        <@spring.formHiddenInput "course.id" />
        <@spring.formHiddenInput "course.code" />
        <@spring.formHiddenInput "course.status" />
        <@spring.formHiddenInput "course.publishDate" />
        <#include "courseForm.ftl" />
        <@formMacro.rtcSubmit "Save" "Cancel" "/admin/course/view/${course.code}"/>
    </@formMacro.rtcForm>
</@layout.layout>
