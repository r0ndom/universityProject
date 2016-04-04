<#--if you change this menu, you have to change MenuItems.class!!!-->
<ul class="nav nav-sidebar">
    <li id="activityMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/activity"/>'>Активность</a></li>
    <li id="newsMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/news"/>'>Новости</a></li>
    <li id="courseMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/course"/>'>Курсы</a></li>
    <li id="userMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/user"/>'>Пользователи</a></li>
    <li id="exportMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/export"/>'>Отчёты</a></li>
    <li id="logsMenuItem" class="navMenuItem"><a href='<@spring.url"/admin/search/logs"/>'>Логи</a></li>
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
