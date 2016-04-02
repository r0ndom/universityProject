<#import "../layout/layout.ftl" as layout/>

<@layout.layout>
<h4><strong><@spring.message "logs.details"/></strong> ${fileName}</h4>
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-12" >
            <textarea style='width:100%' rows='30' id="logsDetailsTextarea" class="form-control">${logsDetails}</textarea>
        </div>
    </div>
</div>
<br>
<div class="row">
    <div class="col-md-12" style="text-align: right">
        <a class="btn btn-default" href="<@spring.url "/admin/search/${menuItem}"/>">Cancel</a>
    </div>
</div>
</@layout.layout>