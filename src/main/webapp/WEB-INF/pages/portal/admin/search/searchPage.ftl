<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<script src="<@spring.url'/resources/js/pages/searchPage.js'/>"></script>

<div id="activityFilter" class="filterForm" style="width: 100%; float: left;">
    <#include "filters/activitySearchFilter.ftl"/>
</div>

<div id="newsFilter" class="filterForm" style="width: 100%; float: left;">
<#include "filters/newsSearchFilter.ftl"/>
</div>

<div id="courseFilter" class="filterForm" style="display: none; width: 100%; float: left;">
<#include "filters/courseSearchFilter.ftl"/>
</div>

<div id="userFilter" class="filterForm" style="display: none;width: 100%; float: left; ">
<#include "filters/userSearchFilter.ftl"/>
</div>

<div id="exportFilter" class="filterForm" style="display: none;width: 100%; float: left; ">
<#include "filters/exportSearchFilter.ftl"/>
</div>

<div id="logsFilter" class="filterForm" style="display: none;width: 100%; float: left; ">
    <#include "filters/logSearchFilter.ftl"/>
</div>

<div id="searchButtons" class="row" style="text-align: right;">
    <button id="search" type="button" class="btn btn-primary">Search</button>
    <button id="reset" type="button" class="btn btn-default">Reset</button>
</div>

<div id="searchTable"></div>

<script type="text/javascript">


    $(".navMenuItem").on("click", function (event) {
        event.preventDefault();
        searchPage.showFilterForm(this.id);
        searchPage.doReset("<@spring.url '/admin/search/dropFilter'/>");

    });

    function init(id) {
        searchPage.showFilterForm(id);
        searchPage.doChangePage(${initPage});
    }
    $("#reset").on("click", function () {
                searchPage.doReset("<@spring.url '/admin/search/dropFilter'/>");
            }
    );

    $("#searchTable").on("click", ".navButton", function (event) {
                event.preventDefault();
                var page = this.getAttribute("page");
                searchPage.doChangePage(page);
            }
    );

    $("#search").on('click', function (event) {
        event.preventDefault();
        searchPage.doSearch();
    });


    $( document ).ready(function() {
        $('.active').each(function(i, obj) {
            init(obj.id)
        });
    });
    function ajaxSessionTimeout()
    {
        window.location.replace("<@spring.url'/login'/>");
    }

    !function( $ )
    {
        $.ajaxSetup({
            statusCode:
            {
                901: ajaxSessionTimeout
            }
        });
    }(window.jQuery);

</script>
</@layout.layout>

