<#import "../../../../datatables.ftl" as datatables/>
<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "course.search.result.page.search"/></strong></h4>
<div id="data">
    <table width="100%" class="table" style="margin-bottom: 5px" id="CourseTable">
        <thead>
        <tr>
            <th><@spring.message "course.search.result.table.name"/></th>
            <th><@spring.message "course.search.result.table.expert"/></th>
            <th><@spring.message "course.search.result.table.term"/></th>
            <th><@spring.message "course.search.result.table.status"/></th>
            <th></th>
        </tr>
        </thead>
<#if courses?has_content>
        <#list courses as course>
            <tr style="vertical-align: middle">
                <td style="vertical-align: middle; width: 25%">
                    <a href="<@spring.url "/admin/course/view/${course.code}" />">${course.name}</a>
                    <p style="font-family: 'Times New Roman', Times, serif; font-style: italic">
                    <#list course.types as type>
                        <@spring.message "courses.types.${type}"/><#if type_has_next>, </#if>
                    </#list></p>
                </td>
                <td style="vertical-align: middle;">
                    <#list course.experts as expert>
                        <p>&nbsp${expert.name}&nbsp${expert.surname}
                            &nbsp${expert.email}</p>
                    </#list>
                </td>
                <td style="vertical-align: middle; width: 25%">${course.startDate?string('dd-MMM-yyyy')}&nbsp;-&nbsp;${course.endDate?string('dd-MMM-yyyy')}</td>
                <td style="vertical-align: middle; text-align: center">
                        <#if "${course.status}" == "DRAFT"> <@formMacro.rtcColorLabel "${course.status}" "label-warning" "course.status."/></#if>
                        <#if "${course.status}" == "PUBLISHED"><@formMacro.rtcColorLabel "${course.status}" "label-success" "course.status."/> </#if>
                        <#if "${course.status}" == "ARCHIVED"> <@formMacro.rtcColorLabel "${course.status}" "label-default" "course.status."/> </#if>
                </td>

                <td style="width: 15%;vertical-align: middle">
                    <#if (course.status??) && (course.status == "DRAFT") >
                        <div class="btn-group" >
                            <@formMacro.splitButton/>
                            <ul class="dropdown-menu" style="min-width: 100px;"  role="menu" >
                                <li id="publicationLi" role="presentation"><a role="menuitem" href="#" tabindex="-1" onclick="PopUpShow('${course.code}')">Publish</a></li>
                                <li id="publicationLi" role="presentation"><a role="menuitem" href="#" tabindex="-1" onclick="ArchiveCourse('${course.code}')">Archive</a></li>
                            <#--<li id="deleteLi" role="presentation"><a role="menuitem" tabindex="-1" href="<@spring.url "/admin/course/delete/${course.code}"/>">Remove</a></li>-->
                            </ul>
                        </div>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>
</div>
<hr style="height: 1px; margin-top: 5px; border-top: 1px solid #ddd;">
<div class="row">
    <div class="col-md-6" style="padding-top: 18px">
        <a  href="<@spring.url "/admin/course/create" />">
            <button class="btn btn-primary">Create New</button>
        </a>
    </div>
    <div class="col-md-6" style="text-align: right">
        <@datatables.addPagination/>
    </div>
</div>
<#else>
    <td>
        There are no courses. <a  href="<@spring.url "/admin/course/create"/>">Click here</a> to add.
    </td>
</#if>

<!-- Modal -->
<div class="modal" style="top: 15%; left: 1%" id="publishCourseModal" tabindex="-1" role="dialog" aria-labelledby="publishCourseModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Cancel</span></button>
                <h4 class="modal-title" id="removeUserModalLabel">Publish Course</h4>
            </div>
            <div class="modal-body">
                The course will be published and visible for all users.
            </div>
            <div class="modal-footer">
                <form id="publishCourseForm" name="publishCourseForm" method="post">
                    <input type="hidden" id="courseCode" name="courseCode"/>
                    <button type="button" class="btn btn-default"  data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-default" style="width: 200px"  onClick="return ActionDeterminator(true);">Publish and Create news</button>
                    <button type="button" class="btn btn-primary" onClick="return ActionDeterminator(false);">Publish</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function PopUpShow(courseCode) {
        $("#courseCode").val(courseCode);
        $('#publishCourseModal').modal('show')
    }
    function PopUpHide() {
        $('#publishCourseModal').modal('hide');
    }
    function ArchiveCourse(code){
        $.ajax({
            type: "GET",
            url: "<@spring.url"/admin/course/archive/"/>" + code,
            success: function () {
                searchPage.doChangePage(${currentPage});
            }
        });
        return true;
    }
    function ActionDeterminator(createNews) {
        $.ajax({
            type: "GET",
            url: "<@spring.url"/admin/course/publish/"/>" + $("#courseCode").val() + "?newsCreated="+createNews,
            success: function () {
                searchPage.doChangePage(${currentPage});
            }
        });
        PopUpHide();
        return true;
    }
</script>
