<div class="row">
    <div class="col-md-6">
        <@formMacro.rtcFormTextInput "export.lable.name" "export.name" "required" ""/>
    </div>
    <div class="col-md-6" >
        <@formMacro.rtcFormSingleSelect "export.lable.exportFormat" "export.exportFormat" formats "required" "" "export.exportFormat." ""/>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <@formMacro.rtcFormSingleSelect "export.lable.exportClass" "export.exportClass" types "required" "" "export.exportClass." ""/>
    </div>
</div>
<hr>
<div class="row">
    <b><@spring.message "export.lable.fields"/></b>
</div>
<div class="row">
    <div id="fields" class="col-md-3 col-md-offset-2"></div>
</div>
<div class="row">
    <div class="col-md-6 col-md-offset-2">
        <a id="addFieldH" href="#">Add Field</a>
    </div>
</div>

<input type="hidden" id="exportCount" name="exportCount" value="">
<script>
    var fields;
    var currentFieldId = 0;
    var fieldsCount = 0;
    $(function() {
        $("#addFieldH").on("click", function (event) {
            event.preventDefault();
            $("#fields").append(getFieldsSelect(fields, ""));
            fieldsCount++;
        });
        $("#exportClass").on("change", function (event) {
            event.preventDefault();
            getFields(true);
        });
    });

    function removeField(field) {
        $("#" + field).remove();
        fieldsCount--;
    }
    function setFieldSelection(field, selection) {
        alert(selection)
        $("#" + field + " select").val(selection);
    }

    function getFieldsSelect(fields, checked) {
        var fieldsSelect = "<div class='form-group' id='" + currentFieldId + "'><div " +
                "class='col-md-10'><label " +
                "for=\"fieldsCount\"></label><select name='fields' class='form-control'>";
        for (var i = 0; i < fields.length; i++) {
            if(fields[i] == checked){
                fieldsSelect += "<option selected>" + fields[i] + "</option>";
            }else{
                fieldsSelect += "<option>" + fields[i] + "</option>";
            }
        }
        fieldsSelect += "</select></div><button style='margin-top: 12px' onclick='removeField(" + currentFieldId + ")" +
                "'>-</button></div>";
        currentFieldId++;
        return fieldsSelect;
    }

    function getFields(clean) {
        var selectedType = $('#exportClass').val();
        var data = 'selectedType=' + encodeURIComponent(selectedType);
        $.ajax({
            url: '<@spring.url "/admin/export/getFields" />',
            data: data,
            type: "GET",
            success: function (response) {
                fields = response;
                if (clean == true) {
                    var div = $("#fields");
                    div.html("");
                } else {
                <#if export.fields??>
                    <#assign i = 0>
                    <#list export.fields as f>
                        $("#fields").append(getFieldsSelect(fields, "${f}"));
                        fieldsCount++;
                        <#assign i = i+1>
                    </#list>
                </#if>
                }

            },
            error: function (xhr, status, error) {
            }
        });
        return false;
    }

    $(function () {
        getFields(false);
        $('#selectedType').change(function (event) {
            getFields(true);
        });
        $("form input[type=submit]").click(function(event){
            if(fieldsCount == 0){
                event.preventDefault();
                alert("Add fields!");
            }
        });
    });
</script>
