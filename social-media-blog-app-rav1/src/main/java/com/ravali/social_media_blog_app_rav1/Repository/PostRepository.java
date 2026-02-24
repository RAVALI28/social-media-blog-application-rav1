package com.ravali.social_media_blog_app_rav1.Repository;


import com.ravali.social_media_blog_app_rav1.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
