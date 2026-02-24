package com.ravali.social_media_blog_app_rav1.Service.Impl;


import com.ravali.social_media_blog_app_rav1.DTO.PostDto;
import com.ravali.social_media_blog_app_rav1.Entity.Post;
import com.ravali.social_media_blog_app_rav1.Exception.ReourceNotFoundException;
import com.ravali.social_media_blog_app_rav1.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    //Util method to Map Entity to DTO
    private PostDto mapEntityToDto(Post post) {
        //Map Post Entity to PostDTO
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    //Util method to map DTO to Entity
    private Post mapDtoToEntity(PostDto postDto) {
        //Map PostDTO to Post Entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }




    @Override
    public PostDto createPost(PostDto postDto){

        //Map PostDTO to Post Entity
        Post post = mapDtoToEntity(postDto);

        //Then save the Entity
        Post savedPost = postRepository.save(post);

        //Map Post Entity to PostDTO
        PostDto savedPostDto = mapEntityToDto(savedPost);
        return savedPostDto;

    }



    @Override
    public List<PostDto> getAll() {
        List<Post> postList = postRepository.findAll();

        //Map Post Entity to PostDTO
        List<PostDto> postDtoList = postList.stream().map(post -> mapEntityToDto(post)).toList();
        return postDtoList;
    }

    @Override
    public PostDto fetchPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("Post", "id", String.valueOf(id)));

        PostDto postDto = mapEntityToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("Post", "id", String.valueOf(id)));

        existingPost.setTitle(postDto.getTitle());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(existingPost);

        return mapEntityToDto(updatedPost);

    }

    @Override
    public void deletePostById(long id) {
        Post postToBeDeleted = postRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(postToBeDeleted);
    }


}
