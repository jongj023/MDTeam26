/**
 * Created by User on 29-4-2016.
 */

$(document).ready(function() {
    var floor;
    var tower;
    var image = document.getElementById('planImage');

    document.getElementById("floor-dropdown").selectedIndex = -1;
    document.getElementById("tower-dropdown").selectedIndex = -1;

    $('#floor-dropdown').change(function() {
        floor = ($(this).val());
        //image.src = "resources/images/" + Image(floor, tower) + ".png";
    });

    $('#tower-dropdown').change(function() {
        tower = ($(this).val());
        //image.src = "resources/images/" + Image(floor, tower) + ".png";
    });



    function Image(floor, tower){
        if(floor == null && tower != null){
            return  "plan" + tower;
        } else if(tower == null && floor != null){
            return "plan" + floor;
        } else if(floor == null && tower == null){
            return "plan";
        } else {
            var planText = floor + tower;
            return "plan" + planText;
        }
    }
});