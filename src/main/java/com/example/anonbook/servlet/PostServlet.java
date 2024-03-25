package com.example.anonbook.servlet;


import com.example.anonbook.model.CommentPostManagement;
import com.example.anonbook.model.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/post")

public class PostServlet extends HttpServlet {
    private final CommentPostManagement postCommentManagement = new CommentPostManagement();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Post> posts = postCommentManagement.getAllPosts();
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), posts);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Post post = objectMapper.readValue(request.getReader(), Post.class);
        CommentPostManagement.addPost(post);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }









}