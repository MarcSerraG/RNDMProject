
$(document).ready(function () {


    $('#CommentThread').click(function() {
        $('#addComment').append("<div th:replace=\"fragment/Dynamic :: writeCommentThread\"></div>");
    });



});