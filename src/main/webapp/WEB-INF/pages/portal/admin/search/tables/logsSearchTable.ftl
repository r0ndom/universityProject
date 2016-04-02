<#import "../../../../datatables.ftl" as datatables/>
<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "logs.result"/></strong></h4>
<div id="data">
    <table width="100%" class="table" style="margin-bottom: 5px" id="LogsTable">
        <thead>
        <tr>
            <th><@spring.message "logs.table.file"/></th>
            <th style="text-align: center"><@spring.message "logs.table.size"/></th>
            <th><@spring.message "logs.table.createdDate"/></th>
            <th></th>
        </tr>
        </thead>
        <#if logs?has_content>
            <#list logs as log>
                <tr style="vertical-align: middle">
                    <td style="vertical-align: middle; width: 25%">
                        <p><a href="<@spring.url "/admin/logs/${log.file}"/>">${log.file}</a></p>
                    </td>
                    <td style="vertical-align: middle; width: 25%; text-align: center">
                        <p>${log.size}</p>
                    </td>
                    <td style="vertical-align: middle; width: 25%">
                        <p>${log.createdDate?string('dd-MMM-yyyy')}</p>
                    </td>
                    <td style="vertical-align: middle; width: 25%">
                        <div class="btn-group">
                            <a href="<@spring.url "/admin/logs/download/${log.file}"/>"><button class="btn btn-default" type="button" style="width: 100px" id="dropdownMenuLogs">Download</button></a>
                        </div>
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
            There are no logs.
        </td>
        </#if>
