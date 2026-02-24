package com.ravali.social_media_blog_app_rav1.Service.Impl;


import com.ravali.social_media_blog_app_rav1.DTO.PostDto;
import com.ravali.social_media_blog_app_rav1.DTO.PostResponse;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAll();

    PostDto fetchPost(long id);

    PostDto updatePostById(PostDto postDto, long id);

    void deletePostById(long id);

    PostResponse getAllPostsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto partialUpdatePostById(PostDto postDto, long id);
}

