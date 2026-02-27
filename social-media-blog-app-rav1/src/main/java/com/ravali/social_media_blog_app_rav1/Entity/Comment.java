package com.ravali.social_media_blog_app_rav1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "BODY")
    private String body;

    // Mapping between Comment and Post (Comment to Post)
    // we use FetchType.LAZY because we want to load post only when we need it
    // we use JoinColumn to specify the foreign key column in the Comment table
                   //  that references the primary key of the Post table
    // we set nullable = false because a comment must be associated with a post
    // we use ManyToOne because many comments can be associated with one post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;



}
