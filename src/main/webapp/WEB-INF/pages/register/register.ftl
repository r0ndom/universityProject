<#import "layout/layout.ftl" as layout/>
<#import "../fieldMacro.ftl" as formMacro />

<@layout.layout>
<@formMacro.rtcForm "user" "/register/save" "user.registrationTitle" "${validationRules}" "multipart/form-data">
    <#include "registerForm.ftl" />
<div class="row" style="margin-right: 88px">
    <@formMacro.rtcSubmit "Register" "Cancel" "/"/>
</div>

</@formMacro.rtcForm>
</@layout.layout>