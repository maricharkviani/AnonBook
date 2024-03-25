package com.example.anonbook.model;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_text")
    private String post_text;
    @Column(name = "post_image")
    private String post_image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private String created_at;

    public Post(String post_text, String post_image, String created_at) {
        this.post_text = post_text;
        this.post_image = post_image;
        this.created_at = created_at;
    }


    public Long getId() {
        return id;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
