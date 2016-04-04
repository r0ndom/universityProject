<#--if you change this menu, you have to change MenuItems.class!!!-->
<ul class="nav nav-sidebar">
    <li id="profileMenuItem" class="navMenuItem"><a href='<@spring.url"/expert/profile/"/>'>Профиль</a></li>
    <li id="orderMenuItem" class="navMenuItem"><a href='<@spring.url"/expert/order"/>'>Заявки</a></li>
    <li id="messageMenuItem" class="navMenuItem"><a href='<@spring.url"/expert/message"/>'>Сообщения</a></li>
</ul>

<script type="text/javascript">
    var mainMenuMap = { "profile": "#profileMenuItem",  "orders": "#orderMenuItem", "messages": "#messageMenuItem"};

    $(document).ready(function() {
    <#--if you change this menu, you have to change MenuItems.class!!!-->
        $(mainMenuMap['${menuItem!""}']).addClass("active")
    });

    $(document).click(function() {
        console.log($("#menu").val());
    });

</script>
