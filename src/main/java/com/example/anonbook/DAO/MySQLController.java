package com.example.anonbook.DAO;

import com.example.anonbook.model.Comment;
import com.example.anonbook.model.Post;
import com.example.anonbook.request.AddPostRequest;
import com.example.anonbook.response.GetCommentResponse;
import com.example.anonbook.response.GetPostResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import request.GetCommentRequest;
import request.GetPostRequest;

import java.util.ArrayList;
import java.util.List;

public class MySQLController implements JDBCController {
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();
    private CriteriaQuery<Post> select;
    private TypedQuery<Post> postTypedQuery;

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

            Post post = new Post(addPostRequests.title(), addPostRequests.imgName(), addPostRequests.time());
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
    public List<GetCommentResponse> getCommentsResponse(GetCommentRequest getCommentsRequest) {
        EntityManager entityManager = JDBCConnector.instance.getEntityManager();
        try {
            TypedQuery<GetCommentResponse> query = entityManager.createQuery("SELECT NEW com.example.anonbook.response.GetCommentResponse(c.id, c.content, c.created_at) FROM Comment c WHERE c.postId = :postId ORDER BY c.createdAt DESC", GetCommentResponse.class);
            query.setParameter("postId", getCommentsRequest.postId());
            return query.getResultList();
        } finally {
            entityManager.close();
        }

    }

    @Override
    public void addComment(int postId, String comment) {
        EntityManager entityManager = JDBCConnector.instance.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Comment commentEntity = new Comment();
            commentEntity.setPostId(postId);
            commentEntity.setContent(comment);
            entityManager.persist(commentEntity);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        } finally {
            entityManager.close();
        }
    }


}
