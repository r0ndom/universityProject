<#import "../../../../fieldMacro.ftl" as formMacro/>
<h4><strong><@spring.message "course.search.result.page.criteria"/></strong></h4>
<div class="form-horizontal">
    <div class="row">
        <div class="col-md-6">
        <@formMacro.rtcFormTextInput "courseFilter.name" "courseFilter.name"/>
            <@formMacro.rtcFormMultiSelect "courseFilter.types" "courseFilter.types" courseCategories "" "height: 65;", "courses.types."/>
        </div>
        <div class="col-md-6">
        <@formMacro.formDateSearch  "courseFilter.dateMoreLessEq" "courseFilter.startDate"/>
        <@formMacro.rtcFormSingleSelect "courseFilter.status" "courseFilter.status" courseStatuses, "", "", "course.status.",  {"" : "All"}/>
            <div class="form-group" >
                <label class="control-label col-md-3" for="expertAutoSelectInput">
                <@spring.message"courseFilter.experts"/>
                </label>
                <div class="col-md-5">
                    <input type="text" class="form-control" id="expertAutoSelectInput"/>
                </div>
            </div>
        <@spring.formHiddenInput "courseFilter.expertCode" />
        </div>
    </div>
    <hr style="height: 1px; margin-top: 5px; margin-bottom: 10px; border-top: 1px solid #ddd;"/>
</div>
<br>

<script>
    $(function() {
        var mapExpertDataId;
        var expertAutoComplete;
        $.ajax({
            type: "GET",
            url: "<@spring.url "/admin/user/getExperts"/>",
            success: function(response){
                mapExpertDataId = response;
                expertAutoComplete = Object.keys(mapExpertDataId);
                $("#expertAutoSelectInput").autocomplete({source: expertAutoComplete});

                $("#expertAutoSelectInput").on("autocompleteselect", function(event,ui){
                    var selectedValue = (ui.item.label);
                    var expertId = mapExpertDataId[selectedValue];
                    $("#expertCode").attr("value",expertId);
                });
                $("#expertAutoSelectInput").on("keyup", function(){
                    var selectedValue = $(this).val();
                    if(mapExpertDataId.hasOwnProperty(selectedValue)){
                        var expertCode = mapExpertDataId[selectedValue];
                        $("#expertCode").attr("value",expertCode);
                    }else{
                        if($("#expertAutoSelectInput").val().length > 0){
                            $("#expertCode").attr("value", "undefined");
                        } else {
                            $("#expertCode").attr("value", null);
                        }
                    }
                });
            }
        });
    });
</script>
