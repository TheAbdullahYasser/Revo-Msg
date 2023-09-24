package com.example.lasttest.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    //    public Post(Integer id, String title, String body, User user) {
//        this.id = id;
//        this.title = title;
//        this.body = body;
//        this.user = user;
//    }
//
    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
