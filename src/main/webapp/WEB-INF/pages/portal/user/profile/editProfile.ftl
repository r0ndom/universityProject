<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>

<@formMacro.rtcForm "user" "/user/profile/update" "user.editProfile" "${validationRules}" "multipart/form-data">
    <@spring.formHiddenInput "user.code" />
    <@spring.formHiddenInput "user.id" />
    <@spring.formHiddenInput "user.registerDate" />
    <@spring.formHiddenInput "user.status" />
    <@spring.formHiddenInput "user.photo" />
    <div class="row" style="width: 120%">
        <#include "userForm.ftl" />
        <div class="row" style="margin-right: 88px;">
            <@formMacro.rtcSubmit "Save" "Cancel" "/user/profile/"/>
        </div>
    </div>

</@formMacro.rtcForm>

</@layout.layout>