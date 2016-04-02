<#ftl strip_whitespace=true>
<#escape x as x?html>
<#include "/spring.ftl"/>

<#macro rtcIncludeLink>
<link href="<@spring.url'/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
<link href="<@spring.url '/resources/css/application.css'/>" rel="stylesheet" type="text/css"/>
<link href="<@spring.url'/resources/css/jquery.tagit.css'/>" rel="stylesheet" type="text/css">
<link href="<@spring.url'/resources/css/tagit.ui-zendesk.css'/>" rel="stylesheet" type="text/css">
<link href="<@spring.url'/resources/js/jquery-ui/jquery-ui.min.css'/>" rel="stylesheet" type="text/css">
</#macro>

<#macro rtcIncludeScript>
<script src="<@spring.url'/resources/js/jquery/jquery-1.11.1.min.js'/>"></script>
<script src="<@spring.url'/resources/js/bootstrap.min.js'/>" type="text/javascript"></script>
<script src="<@spring.url'/resources/js/jquery/jquery.validate.min.js'/>"></script>
<script src="<@spring.url'/resources/js/jquery-ui/jquery-ui.min.js'/>" type="text/javascript" charset="utf-8"></script>
<script src="<@spring.url'/resources/js/tag-it.js'/>" type="text/javascript" charset="utf-8"></script>
</#macro>

<#--
* rtcFieldWrapper

*Just a wrapper for inputs
-->
<#macro rtcFieldWrapper label path>
    <@spring.bind path/>
    <div class="form-group">
        <label  class="control-label col-md-3" for="${path?substring(path?index_of(".")+1,
        path?length)}"><@spring.message "${label}"/></label>
    <div class="col-md-5">
        <#nested>
    </div>
    </div>
</#macro>

<#--
* rtcForm
*
* Create a bootstrap form with validation
*
* @param name form name
* @param action form action url
* @param !!!!title !111 here title is not a plain text but a key from bundle message
* @param validationRules json for jquery validate plugin
-->
<#macro rtcForm name action title validationRules="" enctype="">
<form class="form-horizontal" name="${name}" id="${name}" action="<@spring.url "${action}" />" method="post" enctype="${enctype}">
    <@rtcFormValidation formName="${name}" jsonRules="${validationRules}"/>
    <h4><strong><@spring.message "${title}"/></strong></h4><br/>
    <#nested>
</form>
</#macro>

<#--
* rtcSubmit
*
* create submit input with button and link
*
* @param buttonText a text used on submit button
* @param urlText a text used on link  button
* @param urlAdress href param for a link
-->
<#macro rtcSubmit buttonText urlText urlAddress>
<div class="row">
    <div class="col-md-11" style="text-align: right">
        <input type="submit" class="btn btn-primary" value="${buttonText}"/>
        <#--or-->
        <#--<a href="<@spring.url "${urlAddress}" />">${urlText}</a>-->
        <input type="reset" class="btn btn-default" onClick="location.href= '<@spring.url"${urlAddress}"/>'" value="${urlText}"/>
    </div>
    <div class="col-md-1" style="text-align: right"></div>
</div>
</#macro>

<#macro rtcCancelButton url>
    <div class="span2" style="text-align: right">
        <a class="btn btn-default" class="btn btn-default" href="<@spring.url "${url}"/>" >Cancel</a>
    </div>
</#macro>

<#macro rtcSearchButtons pageUrl>
<div class="row" style="text-align: right">
    <div class="col-md-10" style="text-align: right"> <input type="submit" id="searchButton" class="btn btn-primary" value="Search"/>
        <a class="btn btn-default" href="<@spring.url "${pageUrl}" />">Reset</a>
    </div>
</div>
</#macro>

<#macro rtcSubmitDoOrCancel doText doAddress cancelText cancelAddress disabled="">
<div class="span2" style="text-align: right">
   <#if disabled != "disabled">
    <a href="<@spring.url "${doAddress}" />">
        <input type="submit" class="btn btn-primary" ${disabled}
            <#if doText??>
                value="<@spring.message "${doText}"/>"
            <#else>
                value="Do"
            </#if> />
    </a>
   </#if>
    <a class="btn btn-default" href="<@spring.url "${cancelAddress}" />">
        <#if anotherText??>
            <@spring.message "${cancelText}"/>
        <#else>Cancel</#if>
    </a>
</div>
</#macro>
<#--
* rtcFormCustomInput
*
* !!!! Use this macro if you need a very very very specific input just wrap it in this macro
*
* @param !!!!label!!11 here label is not a plain text but a key from bundle message
* @param path the name of the field to bind to
-->
<#macro rtcFormCustomInput label path>
    <@rtcFieldWrapper label path>
        <#nested>
    </@rtcFieldWrapper>
</#macro>

<#macro rtcColorLabel name class="" messagePrefix="">
     <span class="label ${class}" style="width: 80px; vertical-align: middle; height: 20px; display: table-cell; margin-left: 16px;">
            <#if messagePrefix == "">
            ${name?html}
            <#else>
                <@message "${messagePrefix + name?html}"/>
            </#if>
     </span>
</#macro>


<#--
* rtcForm...
*
* Use this macro form form handling
*
* @param !!!!label!!11 here label is not a plain text but a key from bundle message
* @param path the name of the field to bind to
* @param options  a map (value=label) or list of all the available options if you use input like single select
* @param class  a class  of input
* @param class  style custom css for input
-->

<#macro rtcFormTextInput label path class="" style="">
    <@rtcFieldWrapper label path>
        <@spring.formInput path "class = \"form-control "+"${class}"+"\" "+"${style} " + "${additionalTags.renderAdditionalTags(status, springMacroRequestContext.getModel())}"/>
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormPasswordInput label path class="" style="">
    <@rtcFieldWrapper label path>
        <@spring.formPasswordInput path "class = \"form-control "+"${class}"+"\" "+"${style}" />
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormRadioButtons label path options class="" style="" default="" disabled="" messagePrefix="">
    <@rtcFieldWrapper label path>
        <#--<@bind path/>-->
        <#list options as value>
            <input type="radio" class="${class}" style="${style}" name="${status.expression}" ${disabled} value="${value}"
                <#if value == default> checked </#if>
            <@closeTag/>
            <#if messagePrefix == "">
                ${value?html}
            <#else>
                <@message "${messagePrefix + value?html}"/>
            </#if>
        </#list>
    </@rtcFieldWrapper>
</#macro>

<#macro rtcRadioButtons label path options class="" style="" default="" messagePrefix="">
    <@spring.bind path/>
    <label for="${path?substring(path?index_of(".")+1,path?length)}"><@spring.message "${label}"/></label>
    <br>
    <#list options as value>
        <input type="radio" class="${class}" style="${style}" name="${status.expression}" value="${value}"
            <#if value == default> checked </#if>
        <@closeTag/>
        <#if messagePrefix == "">
            ${value?html}
        <#else>
            <@message "${messagePrefix + value?html}"/>
        </#if>
        <br>
    </#list>
</#macro>

<#macro rtcFormSingleSelect label path options class="" style="" messagePrefix="" noSelection={"" : ""} default="">
    <@rtcFieldWrapper label path>
    <select id="${status.expression?replace('[','')?replace(']','')}" name="${status.expression}" class = "form-control ${class}" style="${style}">
        <#if noSelection?is_hash>
            <#list noSelection?keys as noSelectionKey>
                <option value="${noSelectionKey}"   <#if noSelectionKey == default> selected </#if>>
                    ${noSelection[noSelectionKey]}
                </option>
            </#list>
        </#if>
        <#if options?is_hash>
            <#list options?keys as value>
                <option value=""<@checkSelected value/> <#if value == default> selected </#if>>
                <#-- value="" means that you will receive in controller empty string instead of
                role, status, author e.t.c. so you should provide in search class chech if (myproperty!= null && !"".equals(myproperty) -->
                    <#if messagePrefix == "">
                        ${options[value]?html}
                    <#else>
                        <@message "${messagePrefix + options[value]?html}"/>
                    </#if>
                </option>
            </#list>
        <#else>
            <#list options as value>
                <option value="${value?html}"<@checkSelected value/> <#if value == default> selected </#if>>
                    <#if messagePrefix == "">
                    ${value?html}
                    <#else>
                        <@message "${messagePrefix + value?html}"/>
                    </#if>
                </option>
            </#list>
        </#if>
    </select>
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormMultiSelect label path options class="" style="" messagePrefix="" additionalGetter="">
        <@rtcFieldWrapper label path>
            <#if additionalGetter != "">
                <#assign properties = [""]>
                <#if status.actualValue??>
                <#list status.actualValue as value>
                    <#assign properties = properties + [value[additionalGetter]]>
                </#list>
                </#if>
            </#if>
        <select multiple="multiple" size="3"
                <#if status.expression??>id="${status.expression?replace('[','')?replace(']','')}"</#if>
                name="${status.expression!""}"
                class = "form-control ${class}"  style = "${style}">
            <#if options?is_hash>
                <#list options?keys as value>
                    <#if additionalGetter != "">
                        <#assign isSelected = contains(properties, options[value])>
                    <#else>
                        <#assign isSelected = contains(status.actualValue?default([""]), options[value])>
                    </#if>
                    <option value="${value?html}"<#if isSelected>selected="selected"</#if>>
                        <#if messagePrefix == "">
                            ${options[value]?html}
                        <#else>
                            <@message "${messagePrefix + options[value]?html}"/>
                        </#if>
                    </option>
                </#list>
            <#else>

            <#list options as value>
                <#assign isSelected = contains(status.actualValue?default([""]), value)>
                <option value="${value}"<#if isSelected>selected="selected"</#if>>
                    <#if messagePrefix == "">
                        ${value?html}
                    <#else>
                        <@message "${messagePrefix + value?html}"/>
                    </#if>
                </option>
            </#list>
            </#if>
        </select>
        </@rtcFieldWrapper>
</#macro>

<#macro rtcFormLabelOut label path messagePrefix="" class="">
    <div>
    <label
        style="float: left;
        width: 13em;
        text-align: right;
        margin-bottom: 10px;"
        class="${class}" >
            <@spring.message label/>&nbsp
    </label>
    </div>

    <#if path??>
    <div class="col-md-4">
        <#if path? is_sequence>
            <#list path as tmp>
                <#if  messagePrefix == ""> ${tmp}
                <#else>  <@spring.message "${messagePrefix + tmp}"/>
                </#if>
                <#if tmp_has_next>,</#if>
            </#list>
        <#else>
            <#if  messagePrefix == "">
                <#if path??>${path}<#else>&nbsp</#if>
            <#else>
                <#if path??><@spring.message "${messagePrefix + path}"/><#else>&nbsp</#if>
            </#if>
        </#if>
    </div>
    </#if>
</#macro>

<#macro rtcFormTextarea label path class="" style="">
    <@rtcFieldWrapper label path>
        <@spring.formTextarea path "class = \"form-control "+"${class}"+"\" "+"${style}" />
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormCheckbox label path class="" style="">
    <@rtcFieldWrapper label path>
        <@spring.formCheckbox path "class = \"form-control "+"${class}"+"\" "+"${style}" />
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormCheckboxes label path options class="" style="">
    <@rtcFieldWrapper label path>
        <@spring.formCheckboxes path options "class = \"form-control "+"${class}"+"\" "+"${style}" />
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormUserEmailInput label path class="" style="">
    <@rtcFieldWrapper label path>
        <@spring.formInput path "class = \"form-control "+"${class}"+"\" "+"${style}" />
    </@rtcFieldWrapper>
    <script type="text/javascript" src="<@spring.url'/resources/js/pages/userMailValidation.js'/>"></script>
    <script type="text/javascript">
        $(function () {addMailValidation("<@spring.url "/mailExist/" />", "${user.email!""}")});
    </script>
</#macro>

<#macro rtcFormPasswordInputWithCheckbox label path class="" style="">
    <@rtcFieldWrapper label path>
        <input id="password" name="${status.expression}" type="password" class = "form-control ${class}" ${style}
               value="${stringStatusValue}">
        <div class="controls">
        <label for="showPassword"></label>
        <input id="showPassword"
               onchange="if ($('#password').get(0).type=='password') $('#password').get(0).type='text'; else $('#password').get(0).type='password';"
               name="showPassword" type="checkbox" value="false"
               style="margin: 0px 0px 0px;">  <@spring.message "user.showPassword"/>
        </div>
    </@rtcFieldWrapper>
</#macro>

<#macro rtcFormDateField label path class="" style="">
    <@rtcFieldWrapper path path>
        <@spring.formInput path "class = \"form-control "+"${class}"+"\" "+"${style}"/>
        <script type="text/javascript">
            $(document).ready(function () {
                var selector = "${path?substring(path?index_of(".")+1,path?length)}";
                $("#"+selector).datepicker(
                        {
                            dateFormat: "dd.mm.yy"
                            <#if path?substring(path?index_of(".")+1,path?length) == "birthDate">
                                , changeMonth: true,
                                changeYear: true,
                                yearRange: "-100:+0",
                                maxDate: '-1d'
                            </#if>
                        }
                );
                $("#"+selector).attr('readonly', 'readonly');
            });
        </script>
    </@rtcFieldWrapper>
</#macro>

<#macro rtcDateInput path attributes="">
    <@formInput path attributes/>
<script type="text/javascript">
    $(function () {
        $("#${status.expression?replace('[','')?replace(']','')}").datepicker(
                {
                    dateFormat: "dd.mm.yy"

                    <#if "${status.expression?replace('[','')?replace(']','')}"=="birthDate">
                        , changeMonth: true,
                        changeYear: true,
                        yearRange: "-100:+0",
                        maxDate: '-1d'
                    </#if>
                }
        );
        $("#${status.expression?replace('[','')?replace(']','')}").attr('readonly', 'readonly');
    });
</script>
</#macro>

<#macro formDateSearch pathSingleSelect pathDatepicker class="" style="">
<div class="form-group">
    <label for="compare" class="control-label col-md-3" ><@spring.message pathDatepicker/></label>
    <div id="compare"class="col-md-2"><@formSingleSelect pathSingleSelect, ["=", "<", ">"], 'class=form-control'/></div>
    <div class="col-md-3"><@rtcDateInput  pathDatepicker 'class=form-control style="background-color: #fff"' /></div>
</div>
</#macro>

<#macro rtcFormTagsInput label path attributes="">
    <@rtcFieldWrapper label path>
        <@formHiddenInput path attributes/>
        <ul id="${status.expression?replace('[','')?replace(']','')}Tag"></ul>
        <script type="text/javascript">
            $(function () {
                $('#${status.expression?replace('[','')?replace(']','')}Tag').tagit({
                    singleField: true,
                    singleFieldNode: $('#${status.expression?replace('[','')?replace(']','')}')
                });
            });
        </script>
    </@rtcFieldWrapper>
</#macro>
</#escape>
<#macro rtcFormValidation formName jsonRules >
<script src="<@spring.url'/resources/js/jquery/jquery.validate.min.js'/>"></script>
<script>
    $(document).ready(function () {
        $("#${formName}").validate({
        ${jsonRules}
            submitHandler: function (form) {
                if (!this.wasSent) {
                    this.wasSent = true;
                    $(':submit', form).val('Please wait...')
                            .attr('disabled', 'disabled')
                            .addClass('disabled');
                    form.submit();
                } else {
                    return false;
                }
            }
        });
        $(".required").each(function () {
            var myid = this.id;
            var label = $("label[for=\'" + myid + "\']");
            var text = label.text();
            label.html('* ' + text);
        });
        $.validator.addMethod("pattern", function(value, element, options){
            var re = new RegExp(options);
            return this.optional(element) || re.test(value);
        });
    });
</script>
</#macro>

<#macro capacityIndicator acceptedOrders totalOrders>
    <#assign acceptancePercent = acceptedOrders*100/totalOrders>

    <#if acceptancePercent == 0>
    <img src="<@spring.url'/resources/images/user/c0.png'/>"  alt="..."  data-toggle="tooltip" data-placement="bottom" title="<@spring.message 'course.freeCapacity'/>">
    <#elseif acceptancePercent <= 50>
    <img src="<@spring.url'/resources/images/user/c1.png'/>"  alt="..."  data-toggle="tooltip" data-placement="bottom" title="<@spring.message 'course.freeCapacity'/>">
    <#elseif acceptancePercent gt 50 && acceptancePercent != 100>
    <img src="<@spring.url'/resources/images/user/c2.png'/>"  alt="..."  data-toggle="tooltip" data-placement="bottom" title="<@spring.message 'course.freeCapacity'/>">
    <#else >
    <img src="<@spring.url'/resources/images/user/c3.png'/>"  alt="..."  data-toggle="tooltip" data-placement="bottom" title="<@spring.message 'course.freeCapacity'/>">
    </#if><#--

 -->&nbsp;<span data-toggle="tooltip" data-placement="bottom"
            title="<@spring.message 'course.appliedCapacity'/>">${acceptedOrders}</span>/<#--
       --><span data-toggle="tooltip" data-placement="bottom"
            itle="<@spring.message 'course.totalCapacity'/>">${totalOrders}</span><br/>&nbsp;
</#macro>


<#macro userImage imageId="" id="" class="" alt="image">
    <#if imageId?has_content>
        <#if  imageId?starts_with("http://") || imageId?starts_with("https://")>
            <img id="${id}" src="${imageId}"  class="${class}" alt="${alt}"/>
        <#else>
            <img id="${id}" src="<@spring.url '/image/${imageId}'/>"  class="${class}" alt="${alt}"/>
        </#if>
    <#else>
        <img id="${id}" src="<@spring.url'/resources/images/user/annonimous.png'/>"  class="${class}" alt="${alt}"/>
    </#if>
</#macro>

<#macro splitButton>
<button style="width: 80px; height: 34px" class="btn btn-default dropdown-toggle"  data-toggle="dropdown" type="button">Action</button>
<button style="width: 23px; margin-left: -3px; height: 34px" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
    <span class="caret"></span>
    <span class="sr-only">Toggle Dropdown</span>
</button>
</#macro>

<#macro courseImage types style>
<#if types?size gt 1 >
    <img src="<@spring.url'/resources/images/course/Proj.png'/>"  alt="..." style="${style}"><br/>
    <#else>
        <#if types[0]=="QA">
        <img src="<@spring.url'/resources/images/course/QA.png'/>"  alt="..." style="${style}"><br/>
        <#elseif types[0]=="BA">
        <img src="<@spring.url'/resources/images/course/BA.png'/>"  alt="..." style="${style}"><br/>
        <#elseif types[0]=="DEV">
        <img src="<@spring.url'/resources/images/course/Dev.png'/>"  alt="..." style="${style}"><br/>
        </#if>
    </#if>
</#macro>

<#assign MILLISECONDS_OF_DAY = 1000 * 60 * 60 * 24>
<#assign DAY_OF_WEEK = 7>
<#macro weekSpan startDate endDate>
    <#assign days = ((endDate?date?long - startDate?date?long) / MILLISECONDS_OF_DAY )?round  >
    <#if days < DAY_OF_WEEK >
        <span>${days} day<#if days!=1>s</#if></span>
        <#elseif days % DAY_OF_WEEK == 0>
            <#assign week = (days/DAY_OF_WEEK)>
            <span>${week} week<#if week!=1>s</#if></span>
        <#else>
            <#assign week = (days/DAY_OF_WEEK)?floor>
            <span>${week}-${week+1} weeks</span>
    </#if>
</#macro>

<#macro logo>
<img style="height: 30px; margin-top: 5px;" src="<@spring.url'/resources/images/logo.svg'/>">
</#macro>
