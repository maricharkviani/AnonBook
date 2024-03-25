$(document).ready(function () {
    $('#comment-form').submit(function (event) {
        event.preventDefault();
        var content = $('#comment-content').val();
        var postId = $('#post-id').val();
        addComment(postId, content);
    });
});

function addComment(postId, content) {
    var commentData = {
        postId: postId,
        content: content
    };
    $.ajax({
        type: 'POST',
        url: '/comment',
        contentType: 'application/json',
        data: JSON.stringify(commentData),
        success: function (response) {
            console.log('Comment added successfully');
            getComments(postId);
        },
        error: function (xhr, status, error) {
            console.error('Error adding comment:', error);
        }
    });
}

function getComments(postId) {
    $.ajax({
        type: 'GET',
        url: '/comment?postId=' + postId,
        success: function (response) {
            // Handle success
            console.log('Comments retrieved successfully:', response);
            // Display comments
            displayComments(response);
        },
        error: function (xhr, status, error) {
            // Handle error
            console.error('Error retrieving comments:', error);
        }
    });
}

function displayComments(comments) {
    // Clear previous comments
    $('#comments-container').empty();
    // Append each comment to the comments container
    comments.forEach(function (comment) {
        $('#comments-container').append('<div class="comment">' + comment.content + '</div>');
    });
}
