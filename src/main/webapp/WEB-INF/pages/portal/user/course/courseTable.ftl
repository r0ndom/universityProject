<#import "../../../fieldMacro.ftl" as formMacro/>
<#import "../../../datatables.ftl" as datatables/>



<#list courses as course>

        <div class="row" style=" background-color: #F5F5F5; margin-bottom: 30px;margin-left: 2%; width:98%;  padding-left: 0; min-width: 950px">

            <div class="col-md-12 thumb"
                 style="padding-left: 0; text-align: left;
                     <#if course.status == "ARCHIVED">background-color: #f3eaea;</#if>">

                <div class="" style="padding-left: 0; height: 203px; display: block;" >
                    <@formMacro.courseImage course.types "height: 203px; max-width: 242px; display: block; margin-left: auto; margin-right: auto;" />
                </div>


                <div class="" style="margin-top: 8px; margin-left: 30px; flex: 1 1 0%;">
                    <div class="row">

                        <a href="<@spring.url'/user/courses/courseDetails/${course.code}'/>"
                           class="open-sans-200-25">${course.name} </a>
                        <br/> <!--  name course-->

                    </div>

                    <div class="row" style="display: flex; padding-right: 15px;">

                        <div class="" style="padding-left: 0; display: block;   flex: 1 1 0%;">
                            <div class="" style="padding-top: 10px">
                                <span class="open-sans-normal-16">with ${course.experts?first.name}
                                    &nbsp;${course.experts?first.surname}</span>
                            </div>
                            <!-- expert name -->

                            <div class="" style="padding-top: 10px; padding-bottom: 10px">
                                <span class="description open-sans-normal-16">${course.description}</span>
                            </div>
                            <!-- description -->
                        </div>

                        <div class="" style="display:block; text-align: right; float: right">
                            <div class="" style="padding-top: 10px">
                                <span class="open-sans-normal-16">${course.startDate?date?string('dd MMMM yyyy')}</span> <!-- startDate -->
                            </div>

                            <div class="open-sans-normal-16" style="padding-top: 10px; padding-bottom: 10px">
                                <@formMacro.weekSpan course.startDate course.endDate/><#--week-->
                            </div>
                            <div style="text-align: right">
                                <#if course.status != "ARCHIVED">
                                    <a class="btn btn-success btn-sharp btn-to-course" type="button" style="padding-top: 10px; float: left; width: 155px; height: 45px; text-align: center"
                                       href="<@spring.url'/user/courses/courseDetails/${course.code}'/>">Go to course</a>
                                </#if>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>

</#list>


<div class="row" style="text-align: center">
    <#if lastPage gt 1>
        <#if currentPage != lastPage>
            <a href="#" type="button" class="btn btn-info btn-load-more btn-sharp" id="loadMore" page="${currentPage+1}"><@spring.message "course.loadMore" /></a>
        </#if>
    </#if>
</div> <#-- load more button-->

<script>
    $(function () {
        $.each($('.description'), function () {
            var str = $(this).html();
            $(this).html(shorten(str, 150));
        });
    });

    function shorten(text, maxLength) {
        var ret = text;
        if (ret.length > maxLength) {
            ret = ret.substr(0, maxLength - 3) + "...";
        }
        return ret;
    }
</script>
<#--CURRENT STATE: allways show archived course after published course-->

<#--<#if currentPage == lastPage>-->
<#--<div class="row">-->
<#--<div  class="col-md-offset-6 col-md-6">-->
<#--<button id="withArchiveButton" type="button" class="btn btn-primary btn-xs"><@spring.message "courses.archive"/></button>-->
<#--</div>-->
<#--</div>-->
<#--</#if>-->

<#--<script>-->
<#--$("#withArchiveButton").click(function (event) {-->
<#--var val = $("#withArchive").val();-->
<#--var withArchive;-->
<#--if (val == "false") {-->
<#--withArchive = true;-->
<#--$("#withArchive").val("true");-->
<#--} else {-->
<#--withArchive = false;-->
<#--$("#withArchive").val("false");-->
<#--}-->
<#--//$("#loadMore").setAttribute("page",1);-->
<#--var page = $("#loadMore").getAttribute("page");-->
<#--searchAppend($("#types").val(), page);-->
<#--});-->
<#--</script>-->
