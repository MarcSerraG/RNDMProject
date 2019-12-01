$(document).ready(function () {

    var url = window.location.href;
    if(url.includes("Page")){
        var lastChar = url[url.length -1];

        $('.li' + lastChar).addClass("active");
    }else{
        $('.li1').addClass("active");
    }




});