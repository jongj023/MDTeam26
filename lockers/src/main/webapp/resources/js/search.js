/**
 * Created by User on 29-4-2016.
 */
$(document).ready(function() {
    var image = document.getElementById('planImage'), tower, floor;

    document.getElementById("floor-dropdown").selectedIndex = 5;
    document.getElementById("tower-dropdown").selectedIndex = 3;

    $('#floor-dropdown').change(function() {
        floor = ($(this).val());
    });

    $('#tower-dropdown').change(function() {
        tower = ($(this).val());
        image.src = "resources/images/" + Image(tower) + ".png";
    });

    function Image(tower){
        return "plan" + tower;
    }
});

function refreshTable() {
    initializeLockerTable();
    $('#search').val("");
    setOutput('#locker-output', ALERT_SUCCESS, "Refreshed table", 2000);
}

function submitSearch() {
    var data = {};
    data["searchTower"] = $('#tower-dropdown').val();
    data["searchFloor"] = $('#floor-dropdown').val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/search",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success : function(data) {
            if (data.code == "204" || data.code == "400") {
                $('#search-error').html("<p><strong>Sorry!</strong> " + data.message + "</p>");
                $('#searchError').show();
            } else if (data.code == "200") {
                $('#searchModal').modal('hide');
                var results = 0;
                $('#lockerList').html("");
                $.each(JSON.parse(data.result), function (index, obj) {
                    results = index + 1;
                    addRowToTable(index, obj);
                });
                setOutput('#locker-output', ALERT_SUCCESS, "Successfully returned " + results + " results", 5000);
            }
        },
        error : function(e) {
            $('#lockerWarning').html("<p><strong>Invalid request!</strong> Something went wrong while searching for a locker. " +
                "Perhaps the submitted fields were incorrect?</p>");
            $('#lockerAlreadyExists').show();
        }
    });
}


