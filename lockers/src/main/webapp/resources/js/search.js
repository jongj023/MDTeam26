/**
 * Created by User on 29-4-2016.
 */

$(document).ready(function() {
    $(".dropdown-menu li a").click(function () {
        // HELP HIER FF
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText + ' <span class="caret"></span>');

        var floor;
        var tower;

        var image = document.getElementById('planImage');
        image.src ="resources/images/PlanA.png";

    });

function Image(floor, tower){
    var planText = floor + tower;
    return "plan" + planText + ".png";
    }
});