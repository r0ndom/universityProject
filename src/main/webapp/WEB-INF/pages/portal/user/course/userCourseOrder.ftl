<div class="col-md-3" style="word-wrap: break-word; border: solid 1px #008000;text-align: center;margin-top:10px;
margin-left: 5px;">
    <a href="<@spring.url'/user/courseDetails/${course.code}'/>">${course.name} </a>
    <br>

    <div class="thumbnail">
        <img src="<@spring.url'/resources/images/profile.jpg'/>" alt="..."
             style="width: 200px;height: 120px">
    </div>
    <br>

    <div class="userCourses">${course.description}</div>
    <br>

    <div>${orderStatus}</div>
</div>
