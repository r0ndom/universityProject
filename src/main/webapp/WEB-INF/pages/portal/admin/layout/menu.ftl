<#--if you change this menu, you have to change MenuItems.class!!!-->
<ul class="nav nav-sidebar">
    <li id="activityMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/activity"/>'>Activity</a></li>
    <li id="newsMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/news"/>'>News</a></li>
    <li id="courseMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/course"/>'>Courses</a></li>
    <li id="userMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/user"/>'>Users</a></li>
    <li id="exportMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/export"/>'>Export</a></li>
    <li id="logsMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/logs"/>'>Logs</a></li>
</ul>

<script type="text/javascript">
    var mainMenuMap = { "activity": "#activityMenuItem", "news": "#newsMenuItem", "course": "#courseMenuItem",
        "user": "#userMenuItem", "export": "#exportMenuItem", "logs": "#logsMenuItem", "": "#activityMenuItem" }

    $(document).ready(function() {
    <#--if you change this menu, you have to change MenuItems.class!!!-->
        $(mainMenuMap['${menuItem!""}']).addClass("active")
    });

    $(document).click(function() {
        console.log($("#menu").val());
    });

</script>
