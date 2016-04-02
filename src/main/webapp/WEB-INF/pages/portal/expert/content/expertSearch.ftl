<h3 style="text-align: center"><@spring.message "expert.search" /></h3>

<form class="form-horizontal" name="expert" id="expert"
      action="<@spring.url '/expert/search'/>" method="post">
    <div class="row">
        <div class="span6">
        <@rtcmacros.formItem "expert.name"/>
            <@rtcmacros.formItem "expert.category"/>
            <@rtcmacros.formItem "expert.author"/>
        </div>
        <div class="span6">
        <@rtcmacros.formItem "expert.startDate" "datepiker" 'class="input-medium"'/>
            <@rtcmacros.formItem "expert.tags" "tag"/>
        </div>
    </div>
</form>
