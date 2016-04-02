<#import "../../../../datatables.ftl" as datatables/>
<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "news.search.result.page.header"/></strong></h4>
<div>
    <table width="100%" class="table" style="margin-bottom: 5px" id="NewsTable">
        <thead>
        <tr>
            <th><@spring.message "news.search.result.header.title"/></th>
            <th><@spring.message "news.search.result.header.author"/></th>
            <th><@spring.message "news.search.result.header.created.date"/></th>
            <th><@spring.message "news.search.result.header.status"/></th>
            <th></th>
        </tr>
        </thead>
    <#if news?has_content>
        <#list news as news>
            <tr style="vertical-align: middle">
                <td style="vertical-align: middle; width: 25%">
                    <a href="<@spring.url "/admin/news/${news.code}" />">${news.title}</a>
                </td>
                <#if (news.author)??>
                    <td style="vertical-align: middle">
                        <p>${news.author.name!" "} ${news.author.surname!" "}</p>
                    </td>
                <#else>
                    <td style="vertical-align: middle"></td>
                </#if>
                <td style="vertical-align: middle">
                    <p>${news.createDate?string('dd-MMM-yyyy')!" "}</p>
                </td>
                <#if (news.status)??>
                    <#if (news.status)=="DRAFT">
                        <td style="vertical-align: middle; text-align: center">
                            <@formMacro.rtcColorLabel "Draft" "label-warning"/>
                        </td>
                    <#else>
                        <td style="vertical-align: middle; text-align: center">
                            <@formMacro.rtcColorLabel "Published" "label-success"/>
                        </td>
                    </#if>
                <#else>
                    <td style="vertical-align: middle; text-align: center"></td>
                </#if>

                <td style="width: 15%;vertical-align: middle">
                    <div class="btn-group">
                        <@formMacro.splitButton/>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="min-width: 100px;">
                            <#if (news.status??) && (news.status == "DRAFT") >
                                <li id="publicationLi" role="presentation">
                                    <a role="menuitem" tabindex="-1" href="#" onclick="javascript:publish('${news.code}')">Publish</a>
                                </li>
                            </#if>
                            <li>
                                <a href="#" onclick="PopUpShow('${news.code}')">Remove</a>
                            </li>
                        <#--<li id="deleteLi" role="presentation"><a role="menuitem" tabindex="-1" href="<@spring.url'/admin/news/delete/${news.code}'/>">Remove</a></li>-->
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
        <form  class="inline-box" style="margin: 0px"  name="createNews" action="<@spring.url"/admin/news/create"/>"method="get">
            <button  class="btn btn-primary" type="submit">Create New</button>
        </form>
    </div>
    <div class="col-md-6" style="text-align: right">
    <@datatables.addPagination/>
    </div>
</div>
<#else>
<td>
    There are no news. <a  href="<@spring.url "/admin/news/create"/>">Click here</a> to add.
</td>
</#if>
<!-- Modal -->
<div class="modal" style="top: 15%; left: 1%" id="removeNewsModal" tabindex="-1" role="dialog" aria-labelledby="removeNewsModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="removeNewsModalLabel">Remove News</h4>
            </div>
            <div class="modal-body">
                Are you sure you want to remove this news?
            </div>
            <div class="modal-footer">
                <form name="deleteNews" action="<@spring.url"/admin/news/remove/"/>" method="get">
                    <input type="hidden" id="newsCode" name="newsCode"/>
                    <button type="button" class="btn btn-default"  data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" onClick="PopUpHide()">Remove</button>
                </form>
            </div>
        </div>
    </div>
</div>




<script type="text/javascript">
    function PopUpShow(newsCode) {
        $("#newsCode").val(newsCode);
        $('#removeNewsModal').modal('show')
    }
    function PopUpHide() {
        $('#removeNewsModal').modal('hide');
        var newsCode = $("#newsCode").val();
        $.ajax({
            type: "GET",
            url: '<@spring.url"/admin/news/delete/"/>' + newsCode,
            success: function () {
                searchPage.doSearch(${currentPage});
            }
        });
    }
    function publish(newsCode) {
        $.ajax({
            type: "GET",
            url: "<@spring.url"/admin/news/publish/"/>" + newsCode,
            success: function () {
                searchPage.doSearch(${currentPage});
            }
        });
    }
</script>
