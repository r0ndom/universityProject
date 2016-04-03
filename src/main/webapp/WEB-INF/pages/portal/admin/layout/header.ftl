<div class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">



    <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
            <li style="height: 45px">
                <a style="padding-right: 0px" href='<@spring.url"/admin/profile/"/>'><@formMacro.userImage "${(profileHeader.imageId)!}" "" "profileImg" /></a>
            </li>
            <li style="margin-right: 50px;height: 45px">
                <a href='<@spring.url"/admin/profile/"/>'>${(profileHeader.name)!}</a>
            </li>
            <li style="height: 45px; margin-top: 8px">
                <span>
                    <a href="?lang=en"> en</a>
                    |
                    <a href="?lang=ru">ru </a>
                </span>
            </li>
            <li style="margin-right: 20px">
                <a href='<@spring.url"/logout"/>'><img src="<@spring.url'/resources/images/exit.png'/>"></a>
            </li>
        </ul>
    </div>
</div>

