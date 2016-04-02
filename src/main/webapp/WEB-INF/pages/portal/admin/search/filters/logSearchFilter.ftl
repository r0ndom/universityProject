<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "logs.criteria"/></strong></h4>
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-6">
            <@formMacro.formDateSearch  "logsFilter.dateMoreLessEq" "logsFilter.createdDate"/>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
</div>
<br>
