/**
 * Created by User on 29-4-2016.
 */

    ;
$(document).ready(function() {
    var image = document.getElementById('planImage');
    var tower
    var floor

    document.getElementById("floor-dropdown").selectedIndex = -1;
    document.getElementById("tower-dropdown").selectedIndex = -1;

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

function submitSearch() {
    $('#locker-search').submit();
}


