<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>

<@formMacro.rtcForm "user" "/admin/profile/update" "user.editProfile" "${validationRules}" "multipart/form-data">
    <@spring.formHiddenInput "user.code" />
    <@spring.formHiddenInput "user.id" />
    <@spring.formHiddenInput "user.registerDate" />
    <@spring.formHiddenInput "user.status" />
    <@spring.formHiddenInput "user.photo" />
    <#include "userForm.ftl" />
    <div class="row" style="margin-right: 88px">
        <@formMacro.rtcSubmit "Save" "Cancel" "/admin/profile/"/>
    </div>
</@formMacro.rtcForm>

</@layout.layout>