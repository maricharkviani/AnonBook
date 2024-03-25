function getAllPosts() {
    $.ajax({
        type: 'GET',
        url: '/post',
        success: function (response) {
            console.log('Posts retrieved successfully:', response);
            displayPosts(response);
        },
        error: function (xhr, status, error) {
            console.error('Error retrieving posts:', error);
        }
    });
}

function displayPosts(posts) {
    $('#posts-container').empty();
    posts.forEach(function (post) {
        $('#posts-container').append('<div class="post"><h2>' + post.title + '</h2><p>' + post.content + '</p><a href="post.html?id=' + post.id + '">View Post</a></div>');
    });
}

function getPostById(postId) {
    $.ajax({
        type: 'GET',
        url: '/post?id=' + postId,
        success: function (response) {
            console.log('Post retrieved successfully:', response);
            displayPost(response);
        },
        error: function (xhr, status, error) {
            console.error('Error retrieving post:', error);
        }
    });
}

function displayPost(post) {
    $('#post-container').html('<h2>' + post.title + '</h2><p>' + post.content + '</p>');
}

