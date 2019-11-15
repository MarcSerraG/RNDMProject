$(document).ready(function () {
    iconChange();
    function iconChange() {
        var clima = $("#clima").text();
        var icon = $("#clima");
        var url = 'http://openweathermap.org/img/wn/';
        url = url.concat(clima);
        url = url.concat('@2x.png');
        console.log(url);
        $('#icon').attr('src', url);
    }
});