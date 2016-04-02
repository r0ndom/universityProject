<div class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
    <div class="navbar-header">
        <@formMacro.logo />
    </div>
    <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">

            <li style="height: 45px">
                <a style="padding-right: 0px" href='<@spring.url"/expert/profile/"/>'><@formMacro.userImage "${(profileHeader.imageId)!}" "" "profileImg" /></a>
            </li>
            <li style="margin-right: 50px;height: 45px">
                <a href='<@spring.url"/expert/profile/"/>'>${(profileHeader.name)!}
            <#if profileHeader.unreadMessageCount!=0>
                <span id="headerMessageIndicator" class="badge">${(profileHeader.unreadMessageCount)!}</span></#if></a>
            </li>

            <li style="margin-right: 20px; height: 45px">
                <a href='<@spring.url"/logout"/>'><img src="<@spring.url'/resources/images/exit.png'/>"></a>
            </li>
        </ul>
    </div>
</div>
