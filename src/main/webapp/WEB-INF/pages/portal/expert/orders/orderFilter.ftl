<#import "../../../fieldMacro.ftl" as formMacro />
<h4><strong><@spring.message "export.search.result.page.criteria"/></strong></h4>
<@spring.formHiddenInput "orderFilter.expertCode" />
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-6">
        <@formMacro.rtcFormMultiSelect "orderFilter.courseType" "orderFilter.courseType" courseCategories "" "height: 65;", "courses.types."/>
        </div>
        <div class="col-md-6">
            <div class="row">
                <div class="col-md-12">
                <@formMacro.formDateSearch "orderFilter.dateMoreLessEq", "orderFilter.orderDate"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                <@formMacro.rtcFormSingleSelect "orderFilter.status", "orderFilter.status", orderStatuses, "", "", "order.status.", {"" : "All"}/>
                </div>
            </div>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
</div>
<br>