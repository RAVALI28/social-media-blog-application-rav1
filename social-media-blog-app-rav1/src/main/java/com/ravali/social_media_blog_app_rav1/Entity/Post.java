package com.ravali.social_media_blog_app_rav1.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTENT")
    private String content;


    //Mapping between Post and Comment (Post to Comment)
    //we use Set instead of List because we want to avoid duplicate comments for a post
    //we use cascade = CascadeType.ALL because we want to perform all operations
         //(create, update, delete) on comments when we perform operations on post
    //we use orphanRemoval = true because we want to remove comments when we remove post
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Comment> comments = new HashSet<>();
}
