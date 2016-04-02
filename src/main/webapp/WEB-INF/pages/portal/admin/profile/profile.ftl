<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<style>
    label {
        float: left;
        width: 13em;
        text-align: right;
        margin-bottom: 10px;
    }
</style>
<h4><strong><@spring.message "user.details"/></strong></h4>
<div style="width: 85%">
    <div class="row">
        <div class="col-md-6">
            <div class="row">
                <div class="col-md-12" >
                    <label style="float: left;
        width: 11.9em;
        text-align: right;
        margin-bottom: 10px;" class=""><@spring.message "user.gender"/></label>&nbsp&nbsp&nbsp
                    <#list ["Male", "Female"] as value>
                        <input type="radio" name="gender" disabled value="${value}"
                            <#if value == user.gender> checked </#if>>
                    ${value}
                    </#list>
                </div>


                <div class="col-md-12">
                    <@formMacro.rtcFormLabelOut "user.surname" "${user.surname}" "" "col-md-5"/>
                </div>

                <div class="col-md-12">
                    <@formMacro.rtcFormLabelOut "user.name" "${user.name}" "" "col-md-5"/>
                </div>



                <div class="col-md-12">
                    <@formMacro.rtcFormLabelOut "user.middleName" "${user.middleName}" "" "col-md-5"/>
                </div>

                <div class="col-md-12">
                    <@formMacro.rtcFormLabelOut "user.birthDate" "${user.birthDate?string('dd-MMM-yyyy')}" "" "col-md-5"/>
                </div>

            </div>
        </div>
        <div>

            <div>
                <@formMacro.userImage "${(user.photo)!}" "Img" "img-circle" />
            </div>
        </div>
    </div>

    <hr>

    <div class="row">

        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.email" "${user.email}" "" "col-md-5"/>
        </div>

        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.role" user.authorities "user.role." "col-md-5"/>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.city" "${user.city}" "" "col-md-5"/>
        </div>

        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.phone" "${user.phone}" "" "col-md-5"/>
        </div>
    </div>
    <hr>

    <div class="row">


        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.university" "${user.university}" "" "col-md-5"/>
        </div>

        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.speciality" "${user.speciality}" "" "col-md-5"/>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.faculty" "${user.faculty}" "" "col-md-5"/>
        </div>
    </div>

    <hr>

    <div class="row">

        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.programmingLanguages" user.programmingLanguages  "" "col-md-5"/>
        </div>

        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.english" "${user.english}" "user.english.labels." "col-md-5"/>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "user.note" "${user.note}" "" "col-md-5"/>
        </div>
    </div>
    <hr>

    <div class="row">
        <div style="text-align: right">
            <a href="<@spring.url "/admin/profile/edit" />">
                <input type="submit" class="btn btn-primary" value="<@spring.message "action.edit"/>">
            </a>
        </div>

    </div>

</div>
</@layout.layout>
