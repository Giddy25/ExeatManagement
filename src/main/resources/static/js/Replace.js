$(document).ready(function() {
    $('.replace-link').click(function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var target = $(this).data('target');
        $(target).load(href);
    });
});