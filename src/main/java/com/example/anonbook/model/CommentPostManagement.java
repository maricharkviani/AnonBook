package com.example.anonbook.model;

import com.example.anonbook.DAO.JDBCConnector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CommentPostManagement {
    public List<Comment> getAllCommentsForPost(int postId) {
        EntityManager entityManager = JDBCConnector.instance.getEntityManager();
        try {
            TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.id = :postId ORDER BY c.createdAt DESC", Comment.class);
            query.setParameter("postId", postId);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void addComment(Comment comment) {
        EntityManager entityManager = JDBCConnector.instance.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(comment);
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
