package com.ravali.social_media_blog_app_rav1.Controller;

import com.ravali.social_media_blog_app_rav1.DTO.PostDto;
import com.ravali.social_media_blog_app_rav1.Service.Impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //create post (/api/posts/insertPost)
    @PostMapping("/insertPost")
    public ResponseEntity<PostDto> insertPost(@RequestBody PostDto postDto){
        PostDto createdPostDto  = postService.createPost(postDto);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    //Get all posts (/api/posts/fetchAllPosts)
    @GetMapping("/fetchAllPosts")
    public List<PostDto> fetchAllPosts(){
        return postService.getAll();
    }

    //Get Post by ID (/api/posts/fetchPost/{id})
    @GetMapping("/fetchPost/{id}")
    public ResponseEntity<PostDto> fetchPostById(@PathVariable long id){
           PostDto postDtoById = postService.fetchPost(id);
           return ResponseEntity.ok(postDtoById);
    }

    //Put/update the Post by ID (/api/posts/updatePost/{id})
    @PutMapping("/updatePost/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable long id){
       return ResponseEntity.ok(postService.updatePostById(postDto, id));

    }

    //Delete Post by ID (/api/posts/deletePost/{id})
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Deleted successfully post resource ::" +id);

    }
}
