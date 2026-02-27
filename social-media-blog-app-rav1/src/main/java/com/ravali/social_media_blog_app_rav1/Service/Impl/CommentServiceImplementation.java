package com.ravali.social_media_blog_app_rav1.Service.Impl;

import com.ravali.social_media_blog_app_rav1.DTO.CommentDTO;
import com.ravali.social_media_blog_app_rav1.Entity.Comment;
import com.ravali.social_media_blog_app_rav1.Entity.Post;
import com.ravali.social_media_blog_app_rav1.Exception.ReourceNotFoundException;
import com.ravali.social_media_blog_app_rav1.Repository.CommentRepository;
import com.ravali.social_media_blog_app_rav1.Repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

      // Utility method to map Comment Entity to CommentDTO
    private CommentDTO mapCommentEntityToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    // Utility method to map CommentDTO to Comment Entity
    private Comment mapCommentDTOToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;
    }

    @Override
    public CommentDTO createComment(Long postid, CommentDTO commentDTO) {

        //Fetch the post using postid - Check if the post with the given postid exists
         Post post = postRepository.findById(postid)
        .orElseThrow(() -> new ReourceNotFoundException("Post", "id", String.valueOf(postid)));

        //Map CommentDTO to Comment Entity
       //Comment comment =  mapCommentDTOToEntity(commentDTO);
        //Using the Model Mapper to map  the CommentDTO to Comment Entity
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        //Set the comment to the post
        comment.setPost(post);

        //Save the comment to the database
        Comment savedComment = commentRepository.save(comment);

        //Map the saved Comment entity to CommentDTO and return it
        //CommentDTO savedCommentDTO = mapCommentEntityToDTO(savedComment);
        // Using the Model Mapper to map the saved Comment Entity to CommentDTO
        CommentDTO savedCommentDTO = modelMapper.map(savedComment, CommentDTO.class);

        return savedCommentDTO;
    }



    @Override
    public List<CommentDTO> getCommentsByPostId(Long postid) {

        List<Comment> commentsByPostId = commentRepository.findCommentsByPostId(postid);

        List<CommentDTO>   commentList = commentsByPostId.stream().map(comment -> mapCommentEntityToDTO(comment)).toList();

        return commentList;
    }

    @Override
    public CommentDTO getCommentByCommentId(Long commentid) {
       Comment comment =  commentRepository.findById(commentid)
               .orElseThrow(() -> new ReourceNotFoundException("comment", "commentid", String.valueOf(commentid)));
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        return commentDTO;
    }

    @Override
    public CommentDTO updateCommentByCommentId(CommentDTO commentDTO, Long commentid) {
       Comment existingComment =  commentRepository.findById(commentid)
                .orElseThrow(() -> new ReourceNotFoundException("comment", "commentid", String.valueOf(commentid)));

       //Full update - overwrite all the fields of the existing comment with the values from the incoming commentDTO
       modelMapper.map(commentDTO, existingComment);

       Comment updatedComment = commentRepository.save(existingComment);
      CommentDTO updatedCommentDTO =  modelMapper.map(updatedComment, CommentDTO.class);
        return updatedCommentDTO;
    }

    @Override
    public CommentDTO partialUpdateCommentByCommentId(CommentDTO commentDTO, Long commentid) {
      Comment existingComment =   commentRepository.findById(commentid)
                .orElseThrow(() -> new ReourceNotFoundException("comment", "commentid", String.valueOf(commentid)));

      //Partial update - only update the fields that are not null in the incoming commentDTO
       modelMapper.map(commentDTO, existingComment);

       Comment updatedComment = commentRepository.save(existingComment);
       return modelMapper.map(updatedComment, CommentDTO.class);
    }

    @Override
    public void deleteCommentByCommentId(Long commentid) {
      Comment comment =   commentRepository.findById(commentid)
                .orElseThrow(() -> new ReourceNotFoundException("comment", "commentid", String.valueOf(commentid)));

      commentRepository.deleteById(commentid);
    }

    @Override
    public void deleteAllCommentsByPostId(Long postid) {
        postRepository.findById(postid)
                .orElseThrow(() -> new ReourceNotFoundException("Post", "id", String.valueOf(postid)));

        List<Comment> commentList = commentRepository.findCommentsByPostId(postid);
        commentRepository.deleteAll(commentList);

    }


}
