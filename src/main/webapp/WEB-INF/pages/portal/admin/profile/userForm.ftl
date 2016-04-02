<div class="row">
    <div class="col-md-6">
    <@formMacro.rtcFormRadioButtons "user.gender" "user.gender", ["Male", "Female"] "required", "", "${user.gender}" />
    <@formMacro.rtcFormTextInput  "user.surname" "user.surname" "required" />
    <@formMacro.rtcFormTextInput  "user.name" "user.name" "required" />
    <@formMacro.rtcFormTextInput  "user.middleName" "user.middleName" />
    <@formMacro.rtcFormDateField  "user.birthDate" "user.birthDate" "input-normal required" "style='width: 60%'"/>
    </div>

    <div class="col-md-6">
        <div>
        <@formMacro.userImage "${(user.photo)!}" "photoView" "img-circle" />
        </div>
        <div class="row">
            <div class="col-md-6">
                <div style="margin-left: 10px" class="fileUpload btn-link">
                    <span><u>Upload</u></span>
                    <input type="file" accept="image/*"  onchange="showMyImage(this)"
                           name="uploadPhoto" id="uploadPhoto" class="upload"><br/>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row"><div class="col-md-10"><hr/></div></div>

<div class="row">
    <div class="col-md-6">
    <@formMacro.rtcFormTextInput  "user.email" "user.email" "required" "maxlength='64'"/>
    </div>
    <div class="col-md-6">
    <@formMacro.rtcFormPasswordInputWithCheckbox  "user.password" "user.password" "required" "maxlength='64'"/>
    </div>
</div>
<div class="row"><div class="col-md-10"><hr/></div></div>

<div class="row">
    <div class="col-md-6">
    <@formMacro.rtcFormTextInput  "user.city" "user.city" />
    </div>
    <div class="col-md-6">
    <@formMacro.rtcFormTextInput  "user.phone" "user.phone" "required" />
    </div>
</div>
<div class="row"><div class="col-md-10"><hr/></div></div>

<div class="row">
    <div class="col-md-6">
    <@formMacro.rtcFormTextInput  "user.university" "user.university"/>
        <@formMacro.rtcFormTextInput  "user.faculty" "user.faculty"/>
    </div>
    <div class="col-md-6">
    <@formMacro.rtcFormTextInput  "user.speciality" "user.speciality"/>
    </div>
</div>

<div class="row"><div class="col-md-10"><hr/></div></div>

<div class="row">
    <div class="col-md-6">
    <@formMacro.rtcFormTextInput  "user.programmingLanguages" "user.programmingLanguages" "" "maxlength='255'"/>
    </div>
    <div class="col-md-6">
    <@formMacro.rtcFormSingleSelect "user.english" "user.english" english "required" "" "user.english.labels." {"" : ""}/>
    </div>
</div>
<div class="row"><div class="col-md-10"><hr/></div></div>

<div class="row">
    <div class="col-md-6">
    <@formMacro.rtcFormTextarea  "user.note" "user.note" "required" "rows='3'
        maxlength='255'
        style='width: 376%'"/>
    </div>
</div>

<div class="row"><div class="col-md-10"><hr/></div></div>
<script type="text/javascript" charset="utf8"
        src="<@spring.url'/resources/js/pages/userMailValidation.js'/>"></script>

<script>
    $('label[for="programmingLanguages"]').css({"padding": "0px", "padding-right": "15px"});

    $('#note').bind('input propertychange', function() {
        var text = document.getElementById('note').value;
        var length = text.length;
        var lines = text.substr(0, text.selectionStart).split("\n").length-1;
        var extra = length + lines - 255;
        document.getElementById('note').value = text.substr(0, length - extra);
    });

    var currentMail;
    $(document).ready(function(){
        currentMail = document.getElementById('email').value;
        function limits(obj, limit){
            var text = $(obj).val();
            var length = text.length;
            if(length > limit){
                $(obj).val(text.substr(0,limit));
            }
        }
        $('textarea').keyup(function(){
            limits($(this), 255);
        })
    });

    $(function () {
        addMailValidation("<@spring.url "/mailExist/" />", currentMail)
    });

    function showMyImage(fileInput) {
        var files = fileInput.files;

        var url = fileInput.value;
        var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();

        if ((ext != "jpg")) {fileInput.empty; return;}

        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var imageType = /image.*/;
            if (!file.type.match(imageType)) {
                continue;
            }
            var img=document.getElementById("photoView");
            img.file = file;
            var reader = new FileReader();
            reader.onload = (function(aImg) {
                return function(e) {
                    aImg.src = e.target.result;
                };
            })(img);
            reader.readAsDataURL(file);
        }
    }
</script>
