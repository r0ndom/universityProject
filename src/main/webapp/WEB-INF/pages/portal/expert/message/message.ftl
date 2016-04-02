<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<script src="<@spring.url'/resources/js/pages/searchPage.js'/>"></script>
<div id="messageFilter" class="filterForm" style="width: 100%; float: left; ">
    <#include "messageFilter.ftl"/>
</div>

<div id="searchButtons" class="row" style="text-align: right;">
    <button id="search" type="button" class="btn btn-primary">Search</button>
    <button id="reset" type="button" class="btn btn-default">Reset</button>
</div>

<div id="searchTable"></div>

<!-- Modal -->
<div class="modal" style="top: 15%; left: 1%" id="removeMessageModal" tabindex="-1" role="dialog" aria-labelledby="removeMessageModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="removeMessageModalLabel">Remove News</h4>
            </div>
            <div class="modal-body">
                Are you sure you want to remove this news?
            </div>
            <div class="modal-footer">
                <form name="deleteMessage" >
                    <input type="hidden" id="messageCode" name="messageCode"/>
                    <button type="button" class="btn btn-default"  data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" onClick="PopUpHide()">Remove</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    var serializedFilter = "";

    $(document).ready(function() {
        search(1);
    });

    function search(page) {
        $.ajax({
            type: "GET",
            url: '<@spring.url "/expert/message/messageTable"/>',
            data: serializedFilter + "&page=" + page,
            success: function (result) {
                $("#searchTable").html(result)
            }, error: function (xhr, status, error) {
            }
        });
    }

    $("#searchTable").on("click", ".navButton", function (event) {
        event.preventDefault();
        var page = this.getAttribute("page");
        search(page);
    });

    $("#search").on('click', function (event) {
        event.preventDefault();
        serializedFilter = $("#messageFilter :input").serialize();
        search(1);
    });

    $("#reset").on("click", function () {
                $(".form-horizontal input, textarea").val("");
                $(".form-horizontal ul#tagsTag li.tagit-choice").remove();
                $(".form-horizontal select option:selected").removeAttr("selected");
                serializedFilter = "";
                search(1);
            }
    );

    function shorten(text, maxLength) {
        var ret = text;
        if (ret.length > maxLength) {
            ret = ret.substr(0,maxLength-3) + "...";
        }
        return ret;
    }


    function PopUpShow(messageCode) {
        $("#messageCode").val(messageCode);
        $('#removeMessageModal').modal('show')
    }
    function PopUpHide() {
        $('#removeMessageModal').modal('hide');
        var messageCode = $("#messageCode").val();
        var page = $('#searchTable').find('.active').find('.navButton').attr("page");
        if(page == null) {
            page = 1;
        }
        $.ajax({
            type: "GET",
            url: '<@spring.url"/expert/message/remove/"/>' + messageCode,
            success: function () {
                $.ajax({
                    type: "GET",
                    url: '<@spring.url"/expert/message/unreadMessages"/>',
                    success: function (data) {
                        if(data != 0) {
                            $("#headerMessageIndicator").text(data);
                        } else {
                            $("#headerMessageIndicator").text("");
                        }
                    }
                });
                search(page);
            }
        });
    }

    function ajaxSessionTimeout() {
        window.location.replace("<@spring.url'/login'/>");
    }
    !function( $ ) {
        $.ajaxSetup({
            statusCode: {
                901: ajaxSessionTimeout
            }
        });
    }(window.jQuery);

</script>
</@layout.layout>