<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
    <h4><strong><@spring.message "message.header.messageDetails"/></strong></h4>
<div class="row">
    <div class="col-md-12">
       <textarea readonly rows="3" class="col-md-12" style="resize: none;">${message.description}</textarea>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12" style="text-align: right">
        <a href="<@spring.url "/expert/message"/>" class="btn btn-default"><@spring.message "action.cancel"/></a>
    </div>
</div>
</@layout.layout>