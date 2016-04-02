<#list requests as request>
<div class="row">
    <div class="span12"><a>${request.courseName}:</a></div>
</div>
<div class="row">
    <div class="span9">
    ${request.traineeName} <@spring.message "expert.note" /> ${request.speciality}
    </div>
    <div class="span3"><a
            href="<@spring.url "/expert/accept/${request.orderCode}" />"
            style="color: #008000"><@spring.message "expert.accept" /></a> |
        <a href="<@spring.url "/expert/decline/${request.orderCode}" />"
           style="color: #ff0000"><@spring.message "expert.decline" /></a></div>
</div>
<div class="row">
    <div class="span12">
    ${request.rejectReason}
    </div>
</div>
</#list>
