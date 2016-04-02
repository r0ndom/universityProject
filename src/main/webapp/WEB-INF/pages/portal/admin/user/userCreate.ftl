<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<@formMacro.rtcForm "userName" "/admin/user/save" "user.createUser" "${validationRules}" "multipart/form-data">
    <#include "userForm.ftl" />
    <div class="col-md-11">
        <@formMacro.rtcSubmit "Create" "Cancel" "/admin/search/${menuItem}"/>
    </div>
</@formMacro.rtcForm>
</@layout.layout>
