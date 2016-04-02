<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<style>
    label {
        float: left;
        width: 13em;
        text-align: right;
        margin-bottom: 0px;
    }
</style>
<div style="width: 85%">
<div>
    <h4><strong><@spring.message "news.details"/></strong></h4>
</div>
<div class="row">
    <div class="col-md-6">
        <@formMacro.rtcFormLabelOut "news.title" "${news.title}"/>
    </div>
    <div class="col-md-6">
        <div class="col-md-2">
            <label><@spring.message "news.tags"/></label>
        </div>
        <div class="col-md-4">
            <div>${news.tags?join(", ")}</div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <@formMacro.rtcFormLabelOut "news.description" "${news.description}"/>
    </div>
</div>
<hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
<div class="row">
<div class="col-md-12">
    <div>
        <label style="margin-right: 15px"><@spring.message "news.status"/></label>
        <#if news.status=='DRAFT'>
            <@formMacro.rtcColorLabel "Draft" "label-warning"/>
        <#else>
            <@formMacro.rtcColorLabel "Published" "label-success"/>
        </#if>
    </div>
    <div>
        <#if "${news.status}" == "DRAFT">
            <@formMacro.rtcFormLabelOut "news.creationDate" "${news.createDate?string('dd-MMM-yyyy')}"/>
        <#else>
            <@formMacro.rtcFormLabelOut "news.publishDate" "${news.publishDate?string('dd-MMM-yyyy')}"/>
        </#if>
    </div>
</div>
</div>
<hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
<@formMacro.rtcSubmitDoOrCancel "action.edit" "/admin/news/update/${news.code}" "Cancel" "/admin/search/${menuItem}"/>
</div>
</@layout.layout>
