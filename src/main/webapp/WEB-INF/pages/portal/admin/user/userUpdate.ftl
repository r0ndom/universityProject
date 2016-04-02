<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>

<@formMacro.rtcForm "userName" "/admin/user/update/" "user.editUser" "${validationRules}" "multipart/form-data">
    <@spring.formHiddenInput "user.code" />
    <@spring.formHiddenInput "user.id" />
    <@spring.formHiddenInput "user.registerDate" />
    <@spring.formHiddenInput "user.status" />
    <#if user.photo??><@spring.formHiddenInput "user.photo" /></#if>
    <#include "userForm.ftl" />
    <div class="col-md-11">
        <@formMacro.rtcSubmit "Save" "Cancel" "/admin/user/view/${user.code}"/>
    </div>
</@formMacro.rtcForm>

</@layout.layout>
