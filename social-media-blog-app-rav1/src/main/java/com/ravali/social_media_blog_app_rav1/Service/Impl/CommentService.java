package com.ravali.social_media_blog_app_rav1.Service.Impl;

import com.ravali.social_media_blog_app_rav1.DTO.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(Long postid, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(Long postid);

    CommentDTO getCommentByCommentId(Long commentid);

    CommentDTO updateCommentByCommentId(CommentDTO commentDTO, Long commentid);

    CommentDTO partialUpdateCommentByCommentId(CommentDTO commentDTO, Long commentid);

    void deleteCommentByCommentId(Long commentid);

    void deleteAllCommentsByPostId(Long postid);
}
