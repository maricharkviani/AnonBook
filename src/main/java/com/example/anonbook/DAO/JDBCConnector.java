package com.example.anonbook.DAO;


import com.example.anonbook.model.Comment;
import com.example.anonbook.model.CommentPostManagement;
import com.example.anonbook.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


public class JDBCConnector {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AnonBook");
    private final EntityManager em = emf.createEntityManager();
    private final EntityTransaction et = em.getTransaction();

    private final CriteriaBuilder cb = em.getCriteriaBuilder();
    private CriteriaQuery<Post> cq = cb.createQuery(Post.class);
    private CriteriaQuery<Comment> commentCQ = cb.createQuery(Comment.class);
    private CriteriaQuery<CommentPostManagement> CommentPostManagementCQ = cb.createQuery(CommentPostManagement.class);
    private Root<Post> postRoot = cq.from(Post.class);
    private Root<Comment> commentRoot = commentCQ.from(Comment.class);
    private Root<CommentPostManagement> postCommentRoot = CommentPostManagementCQ.from(CommentPostManagement.class);

    public static JDBCConnector instance;

    public static JDBCConnector getInstance() {
        if (instance == null) {
            instance = new JDBCConnector();
        }
        return instance;
    }

    public void initializeCriteria() {
        cq = cb.createQuery(Post.class);
        postRoot = cq.from(Post.class);
    }

    public void initializeCommentCriteria() {
        commentCQ = cb.createQuery(Comment.class);
        commentRoot = commentCQ.from(Comment.class);
    }

    public void initializePostCommentCriteria() {
        CommentPostManagementCQ = cb.createQuery(CommentPostManagement.class);
        postCommentRoot = CommentPostManagementCQ.from(CommentPostManagement.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public EntityTransaction getEntityTransaction() {
        return et;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return cb;
    }

    public CriteriaQuery<Post> getCriteriaQuery() {
        return cq;
    }

    public CriteriaQuery<Comment> getCommentCriteriaQuery() {
        return commentCQ;
    }

    public CriteriaQuery<CommentPostManagement> getPostCommentCriteriaQuery() {
        return CommentPostManagementCQ;
    }

    public Root<Comment> getCommentRoot() {
        return commentRoot;
    }

    public Root<CommentPostManagement> getPostCommentRoot() {
        return postCommentRoot;
    }

    public Root<Post> getPostRoot() {
        return postRoot;
    }

}
