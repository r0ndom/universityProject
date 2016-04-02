<#import "../../../fieldMacro.ftl" as formMacro />
<h4><strong><@spring.message "message.criteria"/></strong></h4>
<@spring.formHiddenInput "messageFilter.user" />
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-4">
        <@formMacro.rtcFormSingleSelect "messageFilter.status" "messageFilter.status" messageStatus, "", "", "message.status.", {"" : "All"}/>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label for="compare" class="control-label col-md-4" ><@spring.message "messageFilter.sendingDate"/></label>
                <div id="compare"class="col-md-2"><@formMacro.formSingleSelect "messageFilter.dateMoreLessEq", ["=", "<", ">"], 'class=form-control'/></div>
                <div class="col-md-3"><@formMacro.rtcDateInput  "messageFilter.sendingDate" 'class=form-control style="background-color: #fff"' /></div>
            </div>
        </div>

    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
</div>
<br>