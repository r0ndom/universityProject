<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />


<@layout.layout>
<input type="hidden" id="withArchive" value="true">
<input type="hidden" id="varTimePeriod" value="">
<input type="hidden" id="currentCourseType" value="">
<nav class="navbar navbar-inverse navabar-course-filter btn-sharp"
     style="float: none; padding-top: 10px; margin-bottom: 50px; min-width: 950px; width:98%;margin-left: 2%;">

    <div class="row" style="min-width: 950px">
        <div class="col-md-6" >
            <div class="btn-group  period-group" data-toggle="buttons" style="margin-left: 10px; margin-bottom: 10px;">
                <label onclick="setPeriod('')" class="btn btn-sharp btn-default active">
                    <input class="btn-sharp" type="radio" id="asd" name="options" value=""> <@spring.message "courses.period.ALL"/>
                </label>
                <#list periods as period>
                    <label onclick="setPeriod('${period}')" class="btn btn-sharp btn-default">
                        <input  type="radio" name="options"
                               value="${period}"><@spring.message "courses.period.${period}"/></input>
                    </label>
                </#list>
            </div>
        </div>

        <div class="col-md-3">
            <div class="btn-group" style="margin-left: 10px; margin-bottom: 10px;" >
                <button type="button" class="btn-sharp btn btn-default dropdown-toggle "
                        data-toggle="dropdown" aria-expanded="false" style="width: 180px;">
                    <span id="currentType"><@spring.message "courses.types.AllCat"/></span><span class="caret"></span>
                </button>
                <ul  class="dropdown-menu" role="menu">
                    <li><a href="#" class="typeRef" value=""><@spring.message "courses.types.AllCat"/></a></li>
                    <#list courseTypes as type>
                        <li><a href="#" class="typeRef" value="${type}"><@spring.message "courses.types.${type}"/></a></li>
                    </#list>
                </ul>
            </div>
        </div>

        <div class="col-md-3">
            <div class="input-group" style="max-width: 180px; margin-left: 10px; margin-bottom: 10px;">
                <input id="courseKeyword" type="text" class="btn-sharp form-control" placeholder="<@spring.message "courses.placeholder.search"/>">
              <span class="input-group-btn">
                <button onclick="searchKey()" class="btn btn-sharp btn-default" type="button" style="width: 40px">&nbsp;<span class="glyphicon glyphicon-play" aria-hidden="true"></span></button>
              </span>
            </div>
        </div>
    </div>
</nav>

<div id="coursesContent">

</div>

</@layout.layout>

<script>
    $(function () {
        searchReset(1);
    });

    $(".typeRef").click(function (event) {
        event.preventDefault();
        $("#currentType").text($(this).text());
        $("#currentCourseType").val(this.getAttribute("value"));
        searchReset(1);
    });

    function setPeriod(period) {
        $("#varTimePeriod").val(period);
        searchReset(1);
    }

    function searchKey() {
        searchReset(1);
    }

    $(".period-group > .btn").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });

    $("#coursesContent").on("click", ".navButton", function (event) {
        event.preventDefault();
        var page = this.getAttribute("page");
        searchReset(page);
    });

    $(document).on("click", "#loadMore", function (event) {
        event.preventDefault();
        $(this).hide();
        var page = this.getAttribute("page");
        searchAppend(page);

    });




    function appendContent(result) {
        $("#coursesContent").append(result);
    }

    function resetContent(result) {
        $("#coursesContent").html(result);
    }

    function searchAppend(page) {
        search(page, appendContent)
    }

    function searchReset(page) {
        search(page, resetContent)
    }

    function search(page, collbackSucces) {
        var withArchive = $("#withArchive").val();
        var timePeriod = $("#varTimePeriod").val();
        var type = $("#currentCourseType").val();
        var keyword = $("#courseKeyword").val();
        $.ajax({
            type: "POST",
            data: "types=" + type + "&page=" + page + "&withArchived=" + withArchive + "&timePeriod=" + timePeriod + "&keyword=" + keyword,
            url: "<@spring.url "/user/courses/courseTable"/>",
            success: function (result) {
                collbackSucces(result);
            }, error: function (xhr, status, error) {
            }
        });
    }


    //Deprecated

    function setCode(courseCode) {
        document.getElementById("courseCode").value = courseCode;
        $.ajax({
            url: "<@spring.url'/user/courses/position/'/>" + courseCode,
            success: function (result) {

                var res = "";
                result.forEach(function (element, index, array) {
                    res += "<div id='position.name'> " +
                    "<input type='radio' name='position' value='" + element + "'/>&nbsp;" +
                    element +
                    "<br/></div>"
                });
                $("#positions").html(res);
            }
        });
    }

    function PopUpShow(types) {
        types.split(",").forEach(function (type) {
            $("#" + type).show()
        })
        $("#window-popup").show();
    }
    function PopUpHide() {
        $("#window-popup").hide();
    }
</script>