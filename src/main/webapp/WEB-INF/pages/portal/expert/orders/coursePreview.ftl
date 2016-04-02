<#import "../../../fieldMacro.ftl" as formMacro />
<#import "../layout/layout.ftl" as layout/>

<@layout.layout>
<h4><strong><@spring.message "course.details"/></strong></h4>
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "course.name" "${course.name}"/>
        </div>
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "course.startDate" "${course.startDate?date?string('dd-MMM-yyyy')}"/>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "course.category" course.types "courses.types."/>
        </div>
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "course.endDate" "${course.endDate?date?string('dd-MMM-yyyy')}"/>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <@formMacro.rtcFormLabelOut "course.capacity" "${course.capacity}"/>
        </div>
        <div class="col-md-6">
            <#if course.tags?? && course.tags?size-1 != -1>
            <@formMacro.rtcFormLabelOut "course.tags" course.tags/>
        <#else>
                <@formMacro.rtcFormLabelOut "course.tags" "This course doesn't have any tags!"/>
            </#if>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
    <div class="row">
        <div class="col-md-12">
            <@formMacro.rtcFormLabelOut "course.experts" course.experts/>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
    <div class="row">
        <div class="col-md-12">
            <@formMacro.rtcFormLabelOut "course.description" "${course.description}"/>
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
    <div class="row">
        <div class="col-md-6">
            <div>
                <label style="float: left;width: 13em;text-align: right;margin-bottom: 10px; margin-right: 15px">
                    <@spring.message "course.status"/>&nbsp</label>
            </div>
            <#if "${course.status}" == "DRAFT"> <@formMacro.rtcColorLabel "${course.status}" "label-warning" "course.status."/></#if>
            <#if "${course.status}" == "PUBLISHED"><@formMacro.rtcColorLabel "${course.status}" "label-success" "course.status."/> </#if>
            <#if "${course.status}" == "ARCHIVED"> <@formMacro.rtcColorLabel "${course.status}" "label-default" "course.status."/> </#if>
        </div>
        <div class="col-md-6">
            <div class="col-md-3"></div>
            <@formMacro.capacityIndicator course.acceptedOrders  course.capacity/>
        </div>
    </div>
</div>
<hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>

<div class="row">
    <div class="col-md-12" style="text-align: right">
    <a class="btn btn-default" href="<@spring.url "/expert/order/"/>">
        Cancel
    </a>
    </div>
</div>
</@layout.layout>


