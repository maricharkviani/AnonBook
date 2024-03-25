package com.example.anonbook.DAO;

import com.example.anonbook.model.Post;
import com.example.anonbook.request.AddPostRequest;
import com.example.anonbook.response.GetCommentResponse;
import com.example.anonbook.response.GetPostResponse;

import request.GetCommentRequest;
import request.GetPostRequest;

import java.util.List;

public interface JDBCController {
    List<GetPostResponse> addPostResponses();
    void addPost(AddPostRequest addPostRequest);

    Post getPost(GetPostRequest getPostRequest);

    List<GetCommentResponse> getCommentsResponse(GetCommentRequest getCommentsRequest);

    void addComment(int postId, String comment);
}
