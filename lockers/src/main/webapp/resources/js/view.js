/**
 * Created by randyr on 20-4-16.
 */
var currentId = 0;
var usernames = [];
var allUsernames = [];

var ALERT_SUCCESS = "alert alert-success animated fadeInUp",
    ALERT_WARNING = "alert alert-warning animated fadeInUp",
    ALERT_DANGER = "alert alert-danger animated fadeInUp";

$(document).ready(function() {
    $('#lockerWithUsernameExists').hide();
    $('#lockerAlreadyExists').hide();

    getUsers(); // prepare modal autocomplete
    initializeTickets();

    $('#user-form').submit(function(e) {
        var submittedUsername = $('#locker-user').val();
        if (usernames.indexOf(submittedUsername) > -1) {
            //username has already claimed a locker
            e.preventDefault();
            $('#warning-message').html("<p><strong>This username already has a locker!</strong> Cannot assign a second locker.</p>");
            $('#lockerWithUsernameExists').show();
        }
        if (allUsernames.indexOf(submittedUsername) == -1 && submittedUsername.length > 0) {
            // username doesnt exist in DB
            e.preventDefault();
            $('#warning-message').html("<p><strong>Username doesn't exist!</strong> Please enter a valid username</p>");
            $('#lockerWithUsernameExists').show();
        }

    });

    $('.close').click(function (e) {
        e.preventDefault();
        var parent = e.target.parentNode.id;
        $('#' + parent).hide();
    })
});

$(document).ready(function(){
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

function initializeTickets() {
    $.get("/gettickets/"+$('#lockerid').val(), function (data) {
        $('#ticket_body').html("");
        $.each(data, function (index, obj) {
            addTickets(index, obj);
        })
    })
}

$(document).on('change','#ticket-status-select',function(){
    getTicketsWithStatus();
});

function getTicketsWithStatus() {
    $('#ticket_body').html("");
    var show = $('#ticket-status-select').val();
    if (show == "2") {
        initializeTickets();
    } else {
        var data = {};
        data["status"] = show;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getticketswithenabled/"+$('#lockerid').val(),
            data: JSON.stringify(show),
            dataType: 'json',
            timeout: 100000,
            success : function(data) {
                if (data.code == "200") {
                    $.each(JSON.parse(data.result), function (index, obj) {
                        addTickets(index, obj);
                    })
                } else {
                    setOutput('#ticket-output', ALERT_WARNING, data.result, 4000);
                }
            },
            error: function (xhr, status, error) {
                var err = JSON.parse(xhr.responseText);
                setOutput('#ticket-output', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
            }
        });
    }
}

function addTickets(index, obj) {
    var changeTicket, status;
    if (obj.enabled == "1") {
        changeTicket = "<button class='btn btn-danger pull-right close-ticket' data-id='" + obj.ticketid + "' type='button'>Close Ticket</button>";
        status = "success";
    } else if (obj.enabled == "0") {
        changeTicket = "<button class='btn btn-success pull-right open-ticket' data-id='" + obj.ticketid + "' type='button'>Open Ticket</button>";
        status = "danger";
    }
    $('#ticket_body').append(
        "<div class='panel panel-"+ status + "'>" +
        "<div class='panel-heading ticket'>" +
        "<h5 class='col-md-9 col-xs-9'>"+ obj.ticketTitle + "</h5>" +
        "<div class='btn-group col-md-3 col-xs-3 action'>" +
        "<button class='btn btn-warning pull-right edit-ticket' data-id='" + obj.ticketid + "' type='button'><i class='glyphicon glyphicon-pencil' aria-hidden='true'>" +
        "</i> Edit</button>" + changeTicket +
        "</div><div class='clearfix'></div></div>" +
        "<div class='panel-body'>" +
        "<p>" + obj.ticketContent + "</p>" +
        "</div>" +
        "</div>"
    )
}

$(document).on('click', '.edit-ticket', function(event) {
    var ticketid = $(event.target).data("id");
    $.get("/getticket/" + ticketid, function (data) {
        var content = data.ticketContent.split("<br/>");
        bootbox.dialog({
            title: 'Edit ticket',
            message: '<div class="row"><div class="col-md-12 col-xs-12">' +
            '<form><label class="control-label" for="edit_ticket_title">Title:</label>' +
            '<input type="text" name="edit_ticket_title" id="edit_ticket_title" class="form-control" value="'+ data.ticketTitle +'">' +
            '<label for="edit_ticket_content" class="control-label">Content:</label>' +
            '<textarea name="edit_ticket_content" id="edit_ticket_content" class="form-control resize-vertical" rows="5">'+
            content[0] +'</textarea></form>' +
            '</div></div>',
            buttons : {
                success : {
                    label : "Save",
                    className : "btn-success",
                    callback : function () {
                        var ticketTitle = $('#edit_ticket_title').val(), ticketContent = $('#edit_ticket_content').val();
                        if (ticketTitle == "" || ticketContent == "") {
                            setOutput('#ticket_output', ALERT_WARNING, "Cannot save empty fields. Please try again.", 5000);
                        } else {
                            editTicket(ticketid, ticketTitle, ticketContent);
                        }
                    }
                }
            }
        });
    });

});

function editTicket(ticketid, ticketTitle, ticketContent) {
    var data = {};
    data["id"] = ticketid;
    data["ticketTitle"] = ticketTitle;
    data["ticketContent"] = ticketContent;
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/ticket/edit",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            if (data.code = "200") {
                setOutput('#ticket-output', ALERT_SUCCESS, data.message, 4000);
                getTicketsWithStatus();

            } else {
                setOutput('#ticket-output', ALERT_WARNING, data.message, 4000);
            }
        },
        error: function (xhr, status, error) {
            var err = JSON.parse(xhr.responseText);
            setOutput('#ticket-output', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
        }
    });
}

function newTicket() {
    if ($('#add_ticket').length) {
        setOutput('#ticket_output', ALERT_WARNING, "You can only add one ticket at a time", 5000);
    } else {
        $('#ticket_body').prepend(
            "<div class='panel panel-primary' id='add_ticket'>" +
            "<div class='panel-heading'>" +
            "<input class='col-md-9 col-xs-9' required='true' autofocus='true' id='new_ticket_title' placeholder='Title..' style='color:black;'>" +
            "<div class='btn-group col-md-3 col-xs-3 action'>" +
            "<button class='btn btn-danger pull-right cancel-ticket' type='button'><i class='glyphicon glyphicon-remove'></i> Cancel</button>" +
            "<button class='btn btn-success pull-right save-ticket' type='button'><i class='glyphicon glyphicon-floppy-disk'></i> Create ticket</button>" +
            "</div><div class='clearfix'></div></div>" +
            "<div class=panel-body>" +
            "<textarea class='form-control resize-vertical' id='new_ticket_content' rows='5'></textarea>" +
            "</div>" +
            "</div>"
        );
    }

}

$(document).on('click', '.save-ticket', function(event) {
    if ($('#new_ticket_title').val() == "" && $('#new_ticket_content').val() == "") {
        setOutput('#ticket-output', ALERT_WARNING, "Ticket is empty, cannot save it.", 5000);
        return;
    } else {
        bootbox.confirm("Are you sure you would like to save this ticket?", function (result) {
            if (result) {
                var data = {};
                data["id"] = $('#lockerid').val();
                data["ticketTitle"] = $('#new_ticket_title').val();
                data["ticketContent"] = $('#new_ticket_content').val();
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/ticket/save",
                    data: JSON.stringify(data),
                    dataType: 'json',
                    timeout: 100000,
                    success: function (data) {
                        if (data.code = "200") {
                            setOutput('#ticket-output', ALERT_SUCCESS, data.message, 4000);
                            getTicketsWithStatus();

                        } else {
                            setOutput('#ticket-output', ALERT_WARNING, data.message, 4000);
                        }
                    },
                    error: function (xhr, status, error) {
                        var err = JSON.parse(xhr.responseText);
                        setOutput('#ticket-output', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
                    }
                });
            }
        })
    }
});

$(document).on('click', '.cancel-ticket', function(event) {
    if ($('#new_ticket_title').val() == "" && $('#new_ticket_content').val() == "") {
        $('#add_ticket').remove();
    } else {
        bootbox.confirm("Unsaved changes. Are you sure you would like to cancel this ticket?", function (result) {
            if (result) {
                $('#add_ticket').remove();
            }
        })
    }
});

$(document).on('click', '.close-ticket', function(event){
    var ticketid = $(event.target).data("id");
    if (ticketid < 0 || ticketid == null) {setOutput('#ticket-output', ALERT_WARNING, "Bad request, unable to find ticket.", 4000); return;}
    bootbox.confirm("Are you sure you would like to close this ticket?", function (result) {
        if (result) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/ticket/close",
                data: JSON.stringify(ticketid),
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    if (data.code == "200") {
                        setOutput('#ticket-output', ALERT_SUCCESS, data.message, 4000);
                        getTicketsWithStatus();
                    } else {
                        setOutput('#ticket-output', ALERT_WARNING, data.message, 4000);
                        getTicketsWithStatus();
                    }
                },
                error: function (xhr, status, error) {
                    var err = JSON.parse(xhr.responseText);
                    setOutput('#ticket-output', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
                }
            });
        }
    });
});

$(document).on('click', '.open-ticket', function(event){
    var ticketid = $(event.target).data("id");
    if (ticketid < 0 || ticketid == null) {setOutput('#ticket-output', ALERT_WARNING, "Bad request, unable to find ticket.", 4000); return;}
    bootbox.confirm("Are you sure you would like to open this ticket?", function (result) {
        if (result) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/ticket/open",
                data: JSON.stringify(ticketid),
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    if (data.code == "200") {
                        setOutput('#ticket-output', ALERT_SUCCESS, data.message, 4000);
                        getTicketsWithStatus();
                    } else {
                        setOutput('#ticket-output', ALERT_WARNING, data.message, 4000);
                        getTicketsWithStatus();
                    }
                },
                error: function (xhr, status, error) {
                    var err = JSON.parse(xhr.responseText);
                    setOutput('#ticket-output', ALERT_DANGER, "Something went wrong, please try again. " + err.status + " " + err.error, 4000);
                }
            });
        }
    });
});

function getUsers() { //Get list of username, first- and lastname for autocomplete in modal.
    $.get("/getusers", function(data) {
        var sourceArr = [];
        $.each(data, function(index, obj) {
            var all = obj.username + " " + obj.firstname + " " + obj.lastname;
            sourceArr.push(all);
            allUsernames.push(obj.username);
        });
        initializeAutocomplete(sourceArr);
    });
    $.get("/gettakenusers", function (data) {
        usernames = data;
    });
}

function initializeAutocomplete(source) {

    var substringMatcher = function(strs) {
        return function findMatches(q, cb) {
            var matches, substringRegex;

            // an array that will be populated with substring matches
            matches = [];

            // regex used to determine if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');

            // iterate through the pool of strings and for any string that
            // contains the substring `q`, add it to the `matches` array
            $.each(strs, function(i, str) {
                if (substrRegex.test(str)) {
                    matches.push(str.split(" ")[0]);
                }
            });

            cb(matches);
        };
    };

    $('#locker-field .typeahead').typeahead({
            hint: false,
            highlight: true,
            minLength: 1
        },
        {
            name: 'users',
            source: substringMatcher(source)
        });
}

function submitUser() {
    $('#user-form').submit();
}

function clearUserFromLocker(id) {
    $('#locker-id').val(id);
    $('#locker-user').val("");
    $('#user-form').submit();
}

function setExpirationDate() {
    var username = $('#username').text();
    if (username == null || username.length == 0) {
        alert("Cannot add an expiration date to a locker without a user!");
    } else {
        $('#expirationform').submit();
    }
}

function submitEditLocker() {
    var data = {};
    data["lockerid"] = $('#lockerid').val();
    data["lockerTower"] = $('#locker_tower').val();
    data["lockerFloor"] = $('#locker_floor').val();
    data["lockerNumber"] = $('#locker_number').val();

    if (data.lockerNumber == "") {
        $('#lockerWarning').html("<p><strong>Invalid properties!</strong> Please fill in all fields.</p>");
        $('#lockerAlreadyExists').show();
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/editlocker",
            data: JSON.stringify(data),
            dataType: 'json',
            timeout: 100000,
            success : function(data) {
                if (data.code == "204") {
                    console.log(data.message);
                    $('#lockerWarning').html("<p><strong>Invalid locker!</strong> " + data.message + "</p>");
                    $('#lockerAlreadyExists').show();
                } else if (data.code == "200") {
                    location.reload(true);
                }
            },
            error : function(e) {
                console.log(data.lockerFloor + "\t" + data.lockerTower + "\t" + data.lockerNumber);
                $('#lockerWarning').html("<p><strong>Invalid request!</strong> Something went wrong with adding the locker. " +
                    "Perhaps the given properties were invalid?</p>");
                $('#lockerAlreadyExists').show();
            }
        });
    }
}

function setOutput(output, type, message, duration) {
    $(output).addClass(type).html(message);
    setTimeout(function () {
        $(output).removeClass(type).html("");
    }, duration);
}