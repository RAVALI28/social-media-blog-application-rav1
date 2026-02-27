package com.ravali.social_media_blog_app_rav1.Repository;

import com.ravali.social_media_blog_app_rav1.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Find all comments for a specific post id
    List<Comment> findCommentsByPostId(Long postid);



}
