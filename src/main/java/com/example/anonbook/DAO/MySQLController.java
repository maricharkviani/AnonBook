package com.example.anonbook.DAO;

import com.example.anonbook.model.Comment;
import com.example.anonbook.model.CommentPostManagement;
import com.example.anonbook.model.Post;
import com.example.anonbook.request.AddPostRequest;
import com.example.anonbook.response.GetCommentResponse;
import com.example.anonbook.response.GetPostResponse;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import request.GetCommentRequest;
import request.GetPostRequest;

import java.util.ArrayList;
import java.util.List;

public class MySQLController implements JDBCController {
    private CriteriaQuery<Post> select;
    private TypedQuery<Post> postTypedQuery;
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();


    @Override
    public List<GetPostResponse> addPostResponses() {
        jdbcConnector.initializeCriteria();
        select = jdbcConnector.getCriteriaQuery().select(jdbcConnector.getPostRoot());
        postTypedQuery = jdbcConnector.getEntityManager().createQuery(select);

        List<Post> posts = postTypedQuery.getResultList();
        List<GetPostResponse> postResponses = new ArrayList<>();
        posts.forEach(post -> postResponses.add(new GetPostResponse(post.getId(), post.getPost_text(), post.getPost_image(), post.getCreated_at())));

        return postResponses;
    }

    @Override
    public void addPost(AddPostRequest addPostRequests) {
        jdbcConnector.initializeCriteria();

        try {
            jdbcConnector.getEntityTransaction().begin();

            Post post = new Post(addPostRequests.title(), addPostRequests.imgName(),addPostRequests.time());
            System.out.println(post);
            jdbcConnector.getEntityManager().persist(post);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }

    @Override
    public Post getPost(GetPostRequest postRequest) {
        jdbcConnector.initializeCriteria();

        select = jdbcConnector.getCriteriaQuery().select(
                jdbcConnector.getPostRoot()
        ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getPostRoot().get("id"), postRequest.postId()));

        postTypedQuery = jdbcConnector.getEntityManager().createQuery(select);

        return postTypedQuery.getSingleResult();
    }

    @Override
    public List<GetCommentResponse> getCommentsResponse(GetCommentRequest getCommentsRequest){
        jdbcConnector.initializePostCommentCriteria();

        CriteriaQuery<CommentPostManagement> CommentPostManagementCriteriaQuery = jdbcConnector.getPostCommentCriteriaQuery().select(
                jdbcConnector.getPostCommentRoot()
        ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getPostCommentRoot().get("postId"), getCommentsRequest.postId()));

        TypedQuery<CommentPostManagement> postCommentTypedQuery = jdbcConnector.getEntityManager().createQuery(CommentPostManagementCriteriaQuery);

        List<CommentPostManagement> postCommentList = postCommentTypedQuery.getResultList();

        jdbcConnector.initializeCommentCriteria();

        List<GetCommentResponse> getCommentsResponses = new ArrayList<>();

        for (CommentPostManagement commentPostManagement : postCommentList) {
            CriteriaQuery<Comment> comments = null;
//            comments = jdbcConnector.getCommentCriteriaQuery().select(
//                    jdbcConnector.getCommentRoot()
//boloshi unda davamato comentaris id
//            ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getCommentRoot().get("id"), CommentPostManagement.));

            TypedQuery<Comment> commentTypedQuery = jdbcConnector.getEntityManager().createQuery(comments);

            Comment comment = commentTypedQuery.getSingleResult();
            getCommentsResponses.add(new GetCommentResponse(comment.getId(), comment.getContent()));
        }

        return getCommentsResponses;
    }


}
