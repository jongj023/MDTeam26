/**
 * Created by randyr on 20-4-16.
 */
var currentId = 0;
var usernames = [];
var allUsernames = [];
var $rows;

var ALERT_SUCCESS = "alert alert-success animated fadeInUp",
    ALERT_WARNING = "alert alert-warning animated fadeInUp",
    ALERT_DANGER = "alert alert-danger animated fadeInUp";

$(document).ready(function() {
    $(function () { //prepare all tooltips
        $('[data-toggle="tooltip"]').tooltip()
    });

    initializeLockerTable();

    $('#search').keyup(function(e) {
        clearTimeout($.data(this, 'timer'));
        if (e.keyCode == 13)
            search(true);
        else
            $(this).data('timer', setTimeout(search, 500));
    });

    //Hide all errors.
    $('#searchError').hide();

    $('.close').click(function (e) {
        e.preventDefault();
        var parent = e.target.parentNode.id;
        $('#' + parent).hide();
    });

    //Make sure the Modal references the clicked locker when 'Assign' is clicked.
    $('#lockerModal').modal({
        keyboard: true,
        backdrop: false,
        show:false,
    }).on('show.bs.modal', function(event){
        var id = event.relatedTarget.valueOf('id').id;
        $(this).find('.modal-title').html($('<b>Locker ' + id + '</b>'));
        $(this).find('#locker-id').val(id);
    });

});

function setOutput(output, type, message, duration) {
    $(output).addClass(type).html(message);
    setTimeout(function () {
        $(output).removeClass(type).html("");
    }, duration);
}

function initializeLockerTable() {
    $('#lockerList').html("");
    $.get("/getlockers", function (data) {
        $.each(data, function (index, obj) {
            addRowToTable(index, obj);
        });
    });
}

function search(force) {
    var existingString = $("#search").val();
    if (!force) return; //wasn't enter
    if (existingString.length == 0) {
        initializeLockerTable();
    } else {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: "/locker/search",
            data: existingString,
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                if (data.code == "200") {
                    $('#lockerList').html("");
                    $.each($.parseJSON(data.result), function (index, obj) {
                        addRowToTable(index, obj)
                    })
                } else {
                    setOutput('#locker-output', ALERT_WARNING, data.message, 5000);
                }
            },
            error: function (xhr, status, error) {
                var err = JSON.parse(xhr.responseText);
                setOutput('#locker-ouput', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
            }
        });
    }
}

/*
'obj' must be of type LockerEntity.
 */
function addRowToTable(index, obj) {
    var actionColumn =
        "<td class='actions-column'>" +
        "<div class='btn-group' role='group' aria-label='actions-group'>" +
        "<button type='button' class='btn btn-warning claim' data-toggle='modal' data-target='#lockerModal' id='"+ obj.lockerid +"'>" +
        "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span><span class='hidden-xs'> Claim</span></button>" +
        "<button type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
        "<span class='caret'></span><span class='sr-only'>Toggle Dropdown</span></button>" +
        "<ul class='dropdown-menu'>" +
        "<li><a class='pointer' href='/locker/"+ obj.lockerid +"'><span class='glyphicon glyphicon-search' aria-hidden='true'></span> View</a></li>" +
        "</ul>" +
        "</div>" +
        "</td>";

    $('#lockerList').append(
        "<tr class='locker-row' data-id='"+ obj.lockerid +"'>" +
        "<td>" + (index + 1) + "</td><td>"+ obj.lockerTower + obj.lockerFloor + obj.lockerNumber +"</td>" +
        "<td>"+ (obj.user != null ? obj.user.username : "") +"</td>" + actionColumn +
        "</tr>"
    );
}
function submitUser() {
    $('#user-form').submit();
}

$(document).on('click', '.claim', function(event){
    var id = $(event.target).closest('tr').data("id");

    bootbox.confirm("Are you sure you would like to claim this locker?", function (result) {
        if (result) {
            $.ajax({
                type: 'POST',
                contentType: "application/json",
                url: "/locker/setuser",
                data: JSON.stringify(id),
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    if (data.code == "200") {
                        setOutput('#locker-output', ALERT_SUCCESS, data.message, 5000);
                        initializeLockerTable()
                    } else {
                        setOutput('#locker-output', ALERT_WARNING, data.message, 5000);
                    }
                },
                error: function (xhr, status, error) {
                    var err = JSON.parse(xhr.responseText);
                    setOutput('#locker-ouput', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
                }
            });
        }
    });
});

