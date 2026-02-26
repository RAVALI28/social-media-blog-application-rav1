package com.ravali.social_media_blog_app_rav1.Service.Impl;


import com.ravali.social_media_blog_app_rav1.DTO.PostDto;
import com.ravali.social_media_blog_app_rav1.DTO.PostResponse;
import com.ravali.social_media_blog_app_rav1.Entity.Post;
import com.ravali.social_media_blog_app_rav1.Exception.ReourceNotFoundException;
import com.ravali.social_media_blog_app_rav1.Repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public PostResponse getAllPostsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        //Validate / normalize inputs
        if(pageNo < 0) pageNo = 0;
        if(pageSize <= 0) pageSize = 10;
        if(sortBy == null || sortBy.isBlank())  sortBy = "id";
        if(sortDir == null) sortDir = "asc";

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postsPage = postRepository.findAll(pageable);

        List<PostDto> content = postsPage.getContent().stream()
                .map(this::mapEntityToDto)
                .toList();

        PostResponse response = new PostResponse();
        response.setContent(content);
        response.setPageNo(postsPage.getNumber());
        response.setPageSize(postsPage.getSize());
        response.setTotalElements(postsPage.getTotalElements());
        response.setTotalPages(postsPage.getTotalPages());
        response.setLast(postsPage.isLast());
        return response;
    }

    //Utility method
    private boolean isNotEmpty(String value){
        return value != null && !value.isBlank();
    }

    @Override
    public PostDto partialUpdatePostById(PostDto postDto, long id) {

        Post  existingPost = postRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("Post", "id", String.valueOf(id)));
//        if(postDto.getTitle() != null && !postDto.getTitle().isBlank()){
//            existingPost.setTitle(postDto.getTitle());
//        }
//        if(postDto.getDescription() != null && !postDto.getDescription().isBlank()) {
//            existingPost.setDescription(postDto.getDescription());
//        }
//        if(postDto.getContent() != null && !postDto.getContent().isBlank()) {
//            existingPost.setContent(postDto.getContent());
//        }

        //Using utility method for above code
  /*      if(isNotEmpty(postDto.getTitle())){
            existingPost.setTitle(postDto.getTitle());
        }
        if(isNotEmpty(postDto.getDescription())){
            existingPost.setDescription(postDto.getDescription());
        }
        if(isNotEmpty(postDto.getContent())){
            existingPost.setContent(postDto.getContent());
        }


        postRepository.save(existingPost);
        return mapEntityToDto(existingPost);           */


        //Use Model mapper to update the PATCH method and checks the nullchecks if the value is null it will not update the value and if the value is not null it will update the value
        //Using ModelMapper for above code
        modelMapper.map(postDto, existingPost);

        postRepository.save(existingPost);
        return mapEntityToDto(existingPost);
    }


}

