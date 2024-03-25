package com.example.anonbook.servlet;

import com.example.anonbook.DAO.MySQLController;
import com.example.anonbook.model.Comment;
import com.example.anonbook.model.CommentPostManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private final CommentPostManagement CommentPostManagement = new CommentPostManagement();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int postId = Integer.parseInt(request.getParameter("postId"));
        List<Comment> comments = CommentPostManagement.getAllCommentsForPost(postId);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), comments);
        PrintWriter printWriter=response.getWriter();
        printWriter.println(comments);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Comment comment = objectMapper.readValue(request.getReader(), Comment.class);
        CommentPostManagement.addComment(comment);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
