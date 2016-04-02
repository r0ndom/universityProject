<#import "../../../../fieldMacro.ftl" as formMacro/>
<#import "../../../../datatables.ftl" as datatables/>
<h4><strong><@spring.message "export.search.result.page.header"/></strong></h4>
<div>
    <table width="100%" class="table" style="margin-bottom: 5px" id="ExportTable">
        <thead>
        <tr>
            <th><@spring.message "export.search.result.header.name"/></th>
            <th><@spring.message "export.search.result.header.exportType"/></th>
            <th><@spring.message "export.search.result.header.exportFormat"/></th>
            <th><@spring.message "export.search.result.header.createDate"/></th>
            <th></th>
        </tr>
        </thead>
    <#if exports?has_content>
        <#list exports as export>
            <tr style="vertical-align: middle">
                <td style="vertical-align: middle; width: 25%">
                    <a href="<@spring.url "/admin/export/${export.code}" />">${export.name}</a>
                </td>
                <td style="vertical-align: middle">${export.exportClass}</td>
                <td style="vertical-align: middle">${export.exportFormat}</td>
                <td style="vertical-align: middle">${export.createdDate?string('dd-MMM-yyyy')}</td>
                <td style="vertical-align: middle">
                    <div class="btn-group">
                        <@formMacro.splitButton/>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="min-width: 100px;">
                            <li id="downloadLi" role="presentation"><a role="menuitem" tabindex="-1" href="<@spring.url'/admin/export/download/${export.code}'/>">Download</a></li>
                            <li id="removeLi" role="presentation"><a role="menuitem" tabindex="-1" href="<@spring.url'/admin/export/delete/${export.code}'/>">Remove</a></li>
                        </ul>
                    </div>

                </td>
            </tr>
        </#list>
    </table>
</div>
<hr>
<div class="row">
    <div class="col-md-6" style="padding-top: 18px">
        <form  class="inline-box" style="margin: 0px"  name="createNews" action="<@spring.url"/admin/export/create"/>"method="get">
            <button  class="btn btn-primary" type="submit">Create New</button>
        </form>
    </div>
    <div class="col-md-6" style="text-align: right">
    <@datatables.addPagination/>
    </div>
</div>
<#else>
    <td>
        There are no exports. <a  href="<@spring.url "/admin/export/create"/>">Click here</a> to add.
    </td>
</#if>
