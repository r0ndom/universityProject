<link href="<@spring.url'/resources/css/user.css'/>" rel="stylesheet"/>
<div class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
    <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right" style="">
            <li style="height: 45px; margin-top: 8px">
                <form method="get" action="http://localhost:8080">
                    <input hidden="hidden" name="username" value="${(profileHeader.name)!}">
                    <button class="btn btn-primary" type="submit">Чат</button>
                </form>
            <li>
            <li style="height: 45px">
                <a style="padding-right: 0px" href='<@spring.url"/user/profile/"/>'><@formMacro.userImage "${(profileHeader.imageId)!}" "" "profileImg" /></a>
            </li>
            <li style="margin-right: 50px;height: 45px">
                <a href='<@spring.url"/user/profile/"/>' class="lato-bold-20">${(profileHeader.name)!}
                <#if profileHeader.unreadMessageCount!=0>
                    <span id="headerMessageIndicator" class="badge">${(profileHeader.unreadMessageCount)!}</span></#if></a>
            </li>
            <li style="height: 45px; margin-top: 8px">
                <span>
                    <a href="?lang=en" style="color: #ffffff"> en</a>
                    |
                    <a href="?lang=ru" style="color: #ffffff">ru </a>
                </span>
            </li>
            <li style="margin-right: 20px;height: 45px">
                <a href='<@spring.url"/logout"/>'><img src="<@spring.url'/resources/images/exit.png'/>"></a>
            </li>

        </ul>
    </div>
</div>
