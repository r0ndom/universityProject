<#import "../layout/layout.ftl" as layout/>
<#import "../../../fieldMacro.ftl" as formMacro />

<@layout.layout>
<script src="<@spring.url'/resources/js/pages/searchPage.js'/>"></script>
<div id="orderFilter" class="filterForm" style="width: 100%; float: left; ">
    <#include "orderFilter.ftl"/>
</div>

<div id="searchButtons" class="row" style="text-align: right;">
    <button id="search" type="button" class="btn btn-primary">Search</button>
    <button id="reset" type="button" class="btn btn-default">Reset</button>
</div>

<div id="searchTable"></div>

<!-- Modal -->
<div class="modal" style="top: 15%; left: 1%" id="rejectOrderModal" tabindex="-1" role="dialog" aria-labelledby="rejectOrderModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="rejectOrderModalLabel"><@spring.message 'order.modal.header' /></h4>
            </div>
            <div class="modal-body">
                <form name="reasonForm" id="reasonForm">
                <label for="reason" style="font-weight: normal"><@spring.message 'order.modal.reason' /></label>
                <textarea id="reason" name="reason" class="form-control" rows="3" required></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <form name="rejectOrder" >
                    <input type="hidden" id="orderCode" name="orderCode"/>
                    <button type="button" class="btn btn-default"  data-dismiss="modal"><@spring.message 'course.cancel' /></button>
                    <button type="button" class="btn btn-primary" onClick="rejectOrderCall()"><@spring.message 'order.action.reject' /></button>
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
    $("#reasonForm").validate();

    function search(page) {
        $.ajax({
            type: "GET",
            url: '<@spring.url "/expert/order/orderTable"/>',
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
        serializedFilter = $("#orderFilter :input").serialize();
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

    function rejectOrderCall() {
        if ($("#reasonForm").valid()) {
            var orderCode = $("#orderCode").val();
            var reason = $("#reasonForm").serialize();
            $.ajax({
                type: "GET",
                url: '<@spring.url "/expert/order/decline/"/>' + orderCode,
                data: reason,
                success: function (result) {
                    var page = $('#searchTable').find('.active').find('.navButton').attr("page");
                    if(typeof page === 'undefined') {
                        page = 1;
                    }
                    search(page);
                    $('#rejectOrderModal').modal('hide');
                }, error: function (xhr, status, error) {
                }
            });
        }
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
