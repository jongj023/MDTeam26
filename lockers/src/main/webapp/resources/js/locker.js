/**
 * Created by randyr on 20-4-16.
 */
$(document).ready(function() {
    var $rows = $('#locker_table tbody tr');
    $('#search').keyup(function () {
        console.log("search called upon");
        var val = '^(?=.*\\b' + $.trim($(this).val()).split(/\s+/).join('\\b)(?=.*\\b') + ').*$',
            reg = RegExp(val, 'i'),
            text;

        $rows.show().filter(function () {
            text = $(this).text().replace(/\s+/g, ' ');
            return !reg.test(text);
        }).hide();
    });
});