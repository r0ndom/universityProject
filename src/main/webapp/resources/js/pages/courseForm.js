var availableExperts;
var fieldsCount = 0;

function isNumber(event) {
    if (event) {
        var charCode = (event.which) ? event.which : event.keyCode;
        if (charCode != 190 && charCode > 31 &&
            (charCode < 48 || charCode > 57) &&
            (charCode < 96 || charCode > 105) &&
            (charCode < 37 || charCode > 40) &&
            charCode != 110 && charCode != 8 && charCode != 46)
            return false;
    }
    return true;
}

function addExpert() {
    $("#experts").append(getInput());
    $("#expert" + fieldsCount).autocomplete({
        source: availableExperts
    });
    fieldsCount++;
}

function setFieldSelection(field, selection) {
    $("#expert" + field).val(selection);
}

function removeField(field) {
    $("#" + field).remove();
}

function getInput() {
    var fieldsSelect = "<div id=\"" + fieldsCount + "\"><label for=\"fieldsCount\">" +
        "</label><input type='text' class='required expertChecker expertDuplicate' id=\"expert" + fieldsCount + "\" name=\"expertList\"/>";
    fieldsSelect += "<button onclick='removeField(" + fieldsCount + ")' >-</button>"
        + "<br/></div>";
    return fieldsSelect;
}

function prepareCourseFormPage(resourceUrl) {
    $.ajax({
        type: "POST",
        url: resourceUrl,
        success: function (response) {
            availableExperts = response;
        }
    });
    $.validator.addMethod("expertChecker", function (value, element) {
        return $.inArray(value, availableExperts) > -1;
    }, 'Expert do not exist!');

    $.validator.addMethod("expertDuplicate", function (value, element) {
        var counter = 0;
        for (var i = 0; i < fieldsCount; i++) {
            if ($("#expert" + i).val() === value) {
                counter++;
                if (counter > 1) {
                    counter = 0;
                    return false
                }
            }
        }
        return true;
    }, 'Expert already in list');

    $("#endDate").datepicker({
        dateFormat: "dd.mm.yy", minDate: 0
    });
    $("#endDate").attr('readonly', 'readonly');
    $("#startDate").datepicker({
        dateFormat: "dd.mm.yy", minDate: 0,
        onSelect: function (date) {
            var date1 = $('#startDate').datepicker('getDate');
            var date = new Date(Date.parse(date1));
            date.setDate(date.getDate() + 1);
            var newDate = date.toDateString();
            newDate = new Date(Date.parse(newDate));
            $('#endDate').datepicker("option", "minDate", newDate);
        }});
        $("#startDate").attr('readonly', 'readonly');

}





