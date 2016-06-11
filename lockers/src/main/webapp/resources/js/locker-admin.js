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

    //Hide all errors.
    $('#lockerWithUsernameExists').hide();
    $('#lockerAlreadyExists').hide();
    $('#searchError').hide();

    getUsers(); // prepare modal autocomplete
    getLockersWithExpiration(); //Fill expiration table with data.
    getBadge(); //get expiration amount to use as badge.

    $('#search').keyup(function() {

    });

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
        "<button type='button' class='btn btn-warning' data-toggle='modal' data-target='#lockerModal' id='"+ obj.lockerid +"'>" +
        "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span><span class='hidden-xs'> Assign</span></button>" +
        "<button type='button' class='btn btn-warning dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
        "<span class='caret'></span><span class='sr-only'>Toggle Dropdown</span></button>" +
        "<ul class='dropdown-menu'>" +
        "<li><a class='pointer' href='/locker/"+ obj.lockerid +"'><span class='glyphicon glyphicon-search' aria-hidden='true'></span> View</a></li>" +
        "<li><a class='pointer' onclick='clearUserFromLocker("+ obj.lockerid +")'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span> Clear</a></li>" +
        "</ul>" +
        "</div>" +
        "</td>";

    $('#lockerList').append(
        "<tr class='locker-row' data-id='"+ obj.lockerid +"'>" +
        "<td>" + index + "</td><td>"+ obj.lockerTower + obj.lockerFloor + obj.lockerNumber +"</td>" +
        "<td>"+ (obj.user != null ? obj.user.username : "") +"</td>" + actionColumn +
        "</tr>"
    );
}

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

/*
 Used by the 'Clear' button. Submits empty form to clear user from locker.
 */
function clearUserFromLocker(id) {
    $('#locker-id').val(id);
    $('#locker-user').val("");
    $('#user-form').submit();
}

function addLocker() {
    var data = {};
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
            url: "/addlocker",
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
/*Fill expirationTable with data*/
function getLockersWithExpiration() {
    $.get("/getexpirationlockers", function (data) {
        var currentDate = new Date();
        $.each(data, function (index, obj) {
            var expirationDate = new Date(obj.date), result = "", css = "";
            if (currentDate > expirationDate) {css = "danger"; result = "Overdue"}

            $('#expiredTable tbody').append("<tr class=\"locker-row " + css + "\"> " +
                "<td class=\"col-md-1\"> " + result + "</td> " +
                "<td class=\"col-md-1\"><a href=\"/locker/" + obj.lockerid + "\"> " + obj.lockerTower + obj.lockerFloor + obj.lockerNumber + "</a></td> " +
                "<td class=\"col-md-1\" title=\"" + obj.user.firstname +  " "+ obj.user.lastname + "\"> " + obj.user.username + "</td> " +
                "<td class=\"col-md-1\"> " + obj.date + "</td> " +
                "</tr>");
        })
    });
}

/*Shows how many lockers are overdue using a neat little badge.*/
function getBadge() {
    $.get("/getoverdueamount", function (data) {
        $('#badge').text(data);
    })
}

