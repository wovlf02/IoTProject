package com.smartcampus.back.service;

import com.smartcampus.back.dto.ReportRequest;
import com.smartcampus.back.entity.CommentReply;
import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostComment;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.CommentReplyRepository;
import com.smartcampus.back.repository.PostCommentRepository;
import com.smartcampus.back.repository.PostRepository;
import com.smartcampus.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 댓글 관리 서비스
 * 댓글 CRUD, 대댓글 관리, 신고 기능을 처리
 */
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCommentRepository postCommentRepository;
    private final CommentReplyRepository commentReplyRepository;

    // =================== 1. 댓글 CRUD ======================

    /**
     * 댓글 작성
     * @param request 댓글 작성 요청 DTO
     * @return 작성된 댓글 정보
     */
    public PostCommentResponse createComment(PostCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        PostComment comment = PostComment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();

        postCommentRepository.save(comment);

        return new PostCommentResponse(comment);
    }

    /**
     * 특정 게시글의 댓글 목록 조회
     * @param postId 조회할 게시글 ID
     * @return 해당 게시글의 댓글 목록
     */
    public List<PostCommentResponse> getCommentsByPost(Long postId) {
        List<PostComment> comments = postCommentRepository.findByPostId(postId);
        return comments.stream().map(PostCommentResponse::new).collect(Collectors.toList());
    }

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 ID
     * @param request 수정할 내용 DTO
     * @return 수정된 댓글 정보
     */
    public PostCommentResponse updateComment(Long commentId, PostCommentRequest request) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        comment.setContent(request.getContent());
        postCommentRepository.save(comment);

        return new PostCommentResponse(comment);
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 ID
     * @return 삭제 성공 메시지
     */
    public String deleteComment(Long commentId) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        postCommentRepository.delete(comment);
        return "댓글이 삭제되었습니다.";
    }

    // =================== 2. 대댓글 관리 ======================

    /**
     * 대댓글 작성
     * @param request 대댓글 작성 요청 DTO
     * @return 작성된 대댓글 정보
     */
    public CommentReplyResponse createReply(CommentReplyRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        PostComment comment = postCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        CommentReply reply = CommentReply.builder()
                .user(user)
                .comment(comment)
                .content(request.getContent())
                .build();

        commentReplyRepository.save(reply);

        return new CommentReplyResponse(reply);
    }

    /**
     * 특정 댓글의 대댓글 목록 조회
     * @param commentId 조회할 댓글 ID
     * @return 해당 댓글의 대댓글 목록
     */
    public List<CommentReplyResponse> getRepliesByComment(Long commentId) {
        List<CommentReply> replies = commentReplyRepository.findByCommentId(commentId);
        return replies.stream().map(CommentReplyResponse::new).collect(Collectors.toList());
    }

    /**
     * 대댓글 수정
     * @param replyId 수정할 대댓글 ID
     * @param request 수정할 내용 DTO
     * @return 수정된 대댓글 정보
     */
    public CommentReplyResponse updateReply(Long replyId, CommentReplyRequest request) {
        CommentReply reply = commentReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("대댓글을 찾을 수 없습니다."));

        reply.setContent(request.getContent());
        commentReplyRepository.save(reply);

        return new CommentReplyRepository(reply);
    }

    /**
     * 대댓글 삭제
     * @param replyId 삭제할 대댓글 ID
     * @return 삭제 성공 메시지
     */
    public String deleteReply(Long replyId) {
        CommentReply reply = commentReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("대댓글을 찾을 수 없습니다."));

        commentReplyRepository.delete(reply);
        return "대댓글이 삭제되었습니다.";
    }


    // =================== 3. 댓글 및 대댓글 신고 기능 ======================

    /**
     * 댓글 신고
     * @param commentId 신고할 댓글 ID
     * @param request 신고 요청 DTO
     * @return 신고 성공 메시지
     */
    public String reportComment(Long commentId, ReportRequest request) {
        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 신고 기록을 저장하는 로직 추가 가능
        return "댓글이 신고되었습니다.";
    }

    public String reportReply(Long replyId, ReportRequest request) {
        CommentReply reply = commentReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("대댓글을 찾을 수 없습니다."));

        // 신고 기록을 저장하는 로직 추가 가능
        return "대댓글이 신고되었습니다.";
    }
}
