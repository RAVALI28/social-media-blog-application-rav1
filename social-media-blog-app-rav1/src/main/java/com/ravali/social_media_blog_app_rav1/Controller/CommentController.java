package com.ravali.social_media_blog_app_rav1.Controller;

import com.ravali.social_media_blog_app_rav1.DTO.CommentDTO;
import com.ravali.social_media_blog_app_rav1.Service.Impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    //Create comment for a post (/api/comments/posts/{postid}/insertComment)
    @PostMapping("/posts/{postid}/insertComment")
    public ResponseEntity<CommentDTO> insertComment(
            @PathVariable Long postid,
            @RequestBody CommentDTO commentDTO){

        CommentDTO savedComment = commentService.createComment(postid, commentDTO);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);

    }

    //Get all comments by post id (/posts/{postid}/fetchComments)
    @GetMapping("/posts/{postid}/fetchComments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable Long postid){
        List<CommentDTO> commentsByPostId = commentService.getCommentsByPostId(postid);
        return ResponseEntity.ok(commentsByPostId);
    }


    //Get a single comment by comment id (/fetchComment/{commentid})
    @GetMapping("/fetchComment/{commentid}")
    public ResponseEntity<CommentDTO> getSingleCommentByCommentId(@PathVariable Long commentid){
        CommentDTO commentByPostIdAndCommentId = commentService.getCommentByCommentId(commentid);
        return new ResponseEntity<>(commentByPostIdAndCommentId, HttpStatus.OK);
    }


    // Put update the comment  by comment id (/updateComment/{commentid})
    @PutMapping("/updateComment/{commentid}")
    public CommentDTO updateCommentByCommentId(@RequestBody CommentDTO commentDTO, @PathVariable Long commentid){
        return commentService.updateCommentByCommentId(commentDTO, commentid);
    }

     // Patch update the comment  by comment id (/updateCommentPartially/{commentid})
    @PatchMapping("/updateCommentPartially/{commentid}")
    public CommentDTO updateCommentPartiallyByCommentId(@PathVariable Long commentid, @RequestBody CommentDTO commentDTO){
        return  commentService.partialUpdateCommentByCommentId(commentDTO, commentid);
    }

    // Delete the comment by comment id (/deleteComment/{commentid})
    @DeleteMapping("/deleteComment/{commentid}")
    public ResponseEntity<String> deleteCommentByCommentId(@PathVariable Long commentid) {
        commentService.deleteCommentByCommentId(commentid);
        return ResponseEntity.ok("Comment with id " + commentid + " has) been deleted successfully.");
    }

    //Delete all comments by post id (/posts/{postid}/deleteComments)
    @DeleteMapping("/posts/{postid}/deleteComments")
    public ResponseEntity<String> deleteAllCommentsByPostId(@PathVariable Long postid){
        commentService.deleteAllCommentsByPostId(postid);
        return ResponseEntity.ok("All comments for post with id " + postid + " have been deleted successfully.");
    }




}
