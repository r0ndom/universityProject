<#import "../../../fieldMacro.ftl" as formMacro />
<#import "../../../datatables.ftl" as datatables/>
<h4><strong><@spring.message "message.search.result.page.header"/></strong></h4>
<div>
<table width="100%" class="table" style="margin-bottom: 5px" id="ReportTable">
    <thead>
    <tr>
        <th class="col-md-5"><@spring.message "message.search.result.header.subject"/></th>
        <th><@spring.message "message.search.result.header.from"/></th>
        <th><@spring.message "message.search.result.header.received"/></th>
        <th class="col-md-2"></th>
    </tr>
    </thead>
<#if messages?has_content>
    <#list messages as message>
        <tr style="vertical-align: middle">

            <td style="vertical-align: middle">
            <a href="<@spring.url "/expert/message/"/>${message.code}"><span class="description">${message.subject}</span></a>
            </td>

            <td style="vertical-align: middle">
            ${message.userName}
            </td>

            <td style="vertical-align: middle">
            ${message.sendingDate?string('dd-MMM-yyyy')!" "}
            </td>

            <td style="vertical-align: middle">
                <button type="button" class="btn btn-default" onclick="PopUpShow('${message.code}')"><@spring.message "message.action.Remove"/></button>
            </td>
        </tr>
    </#list>
</table>
</div>
<hr>
<div class="row">
    <div class="col-md-12" style="text-align: right">
        <@datatables.addPagination/>
    </div>
</div>
<#else>
    <td>
        There are no messages yet.
    </td>
</#if>

<script>
    $(function() {
        $.each($('.description'), function() {
            var str = $(this).html();
            $(this).html(shorten(str, 50));
        });
    });
    function doAjaxCall(url) {
        $.ajax({
            type: "GET",
            url: url,
            success: function (result) {
                search(${currentPage}); // todo
            }, error: function (xhr, status, error) {
            }
        });
    }
</script>
