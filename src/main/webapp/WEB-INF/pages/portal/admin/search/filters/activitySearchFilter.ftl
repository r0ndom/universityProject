<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "activity.criteria"/></strong></h4>
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormTextInput "activity.filter.user" "activityFilter.user" />
            <@formMacro.rtcFormMultiSelect "activity.filter.entity" "activityFilter.entity", activityEntities, "" "height: 65;"/>
        </div>
        <div class="col-md-6">
            <@formMacro.formDateSearch  "activityFilter.dateMoreLessEq" "activityFilter.date"/>
            <@formMacro.rtcFormMultiSelect "activity.filter.action" "activityFilter.action", activityActions, "" "height: 65;"/>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
</div>
<br>

<script type="text/javascript">
</script>

