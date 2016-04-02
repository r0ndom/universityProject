<div class="">

    <div class="span12">
        <label>Available courses</label>
    </div>

    <div class="container" style="margin-left: -20px;padding-right: 0px">
    <#list courses as course>
        <div class="span4"style="word-wrap: break-word; border: solid 1px #008000;text-align: center;margin-top:10px; margin-left: 5px;">
            <a href="#">${course.name} </a>
            <br>
            <div class="thumbnail">
                <img src="<@spring.url'/resources/images/profile.jpg'/>"
                     alt="..." style="width: 200px;height: 120px">
            </div>
            <br>
            <div class="userCourses"> ${course.description}</div>
        </div>
    </#list>
    </div>

</div>
