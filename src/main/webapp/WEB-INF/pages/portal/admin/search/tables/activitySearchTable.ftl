<#import "../../../../datatables.ftl" as datatables/>
<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "activity.result"/></strong></h4>
<div id="data">
    <table width="100%" class="table" style="margin-bottom: 5px" id="ActivityTable">
        <thead>
        <tr>
            <th><@spring.message "activity.table.details"/></th>
            <th><@spring.message "activity.table.action"/></th>
            <th><@spring.message "activity.table.user"/></th>
            <th><@spring.message "activity.table.time"/></th>
        </tr>
        </thead>
    <#if activities?has_content>
        <#list activities as activity>
            <tr style="vertical-align: middle;">
                <td style="vertical-align: middle">
                    <p>${activity.detail}</p>
                </td>
                <td style="vertical-align: middle;">
                    <p>${activity.action}</p>
                </td>
                <td style="vertical-align: middle;">
                    <p>${activity.username}</p>
                </td>
                <td style="vertical-align: middle;">
                    ${activity.actionDate?string('dd-MMM-yyyy HH:mm:ss')}
                </td>
            </tr>
        </#list>
    </table>
</div>
<hr style="height: 1px; margin-top: 5px; border-top: 1px solid #ddd;">
<div class="row">
    <div class="col-md-12" style="text-align: right">
        <@datatables.addPagination/>
    </div>
</div>
    <#else>
    <td>
        There are no activity.
    </td>
    </#if>