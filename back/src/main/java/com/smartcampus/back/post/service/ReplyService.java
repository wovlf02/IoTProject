package com.smartcampus.back.post.service;

import com.smartcampus.back.post.dto.reply.ReplyCreateRequest;
import com.smartcampus.back.post.dto.reply.ReplyResponse;
import com.smartcampus.back.post.dto.reply.ReplyUpdateRequest;
import com.smartcampus.back.post.entity.Comment;
import com.smartcampus.back.post.entity.Reply;
import com.smartcampus.back.post.exception.CommentNotFoundException;
import com.smartcampus.back.post.exception.ReplyNotFoundException;
import com.smartcampus.back.post.repository.CommentRepository;
import com.smartcampus.back.post.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 대댓글(Reply) 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 댓글에 대한 답글 생성, 수정, 삭제, 좋아요, 1:1 채팅 진입 URL 제공 기능을 포함합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    // 추후 연동될 채팅 서비스 인터페이스
    // private final ChatService chatService;

    /**
     * 댓글에 대한 대댓글(답글)을 생성합니다.
     */
    public ReplyResponse createReply(Long postId, Long commentId, ReplyCreateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다."));

        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("해당 댓글은 지정된 게시글에 속하지 않습니다.");
        }

        Reply reply = Reply.builder()
                .comment(comment)
                .writerId(request.getWriterId())
                .content(request.getContent())
                .build();

        Reply saved = replyRepository.save(reply);

        return buildReplyResponse(saved);
    }

    /**
     * 대댓글의 내용을 수정합니다.
     */
    public ReplyResponse updateReply(Long postId, Long commentId, Long replyId, ReplyUpdateRequest request) {
        Reply reply = replyRepository.findByIdAndCommentId(replyId, commentId);

        if (reply == null) {
            throw new ReplyNotFoundException("해당 대댓글이 존재하지 않습니다.");
        }

        if (!reply.getComment().getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("해당 대댓글은 지정된 게시글에 속하지 않습니다.");
        }

        reply.setContent(request.getContent());
        reply.setUpdatedAt(LocalDateTime.now());

        Reply updated = replyRepository.save(reply);

        return buildReplyResponse(updated);
    }

    /**
     * 대댓글을 삭제합니다.
     */
    public void deleteReply(Long postId, Long commentId, Long replyId) {
        Reply reply = replyRepository.findByIdAndCommentId(replyId, commentId);

        if (reply == null) {
            throw new ReplyNotFoundException("해당 대댓글이 존재하지 않습니다.");
        }

        if (!reply.getComment().getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("해당 대댓글은 지정된 게시글에 속하지 않습니다.");
        }

        replyRepository.delete(reply);
    }

    /**
     * 대댓글 응답 DTO 생성 + 확장 포인트 포함 (좋아요, 채팅 등)
     */
    private ReplyResponse buildReplyResponse(Reply reply) {
        return ReplyResponse.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .writerId(reply.getWriterId())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .liked(false) // 기본값 또는 replyLikeService.isLiked(reply.getId(), userId)
                .likeCount(0) // replyLikeService.countLikes(reply.getId())
                .chatEntryUrl("/api/chat/start?userId=" + reply.getWriterId()) // 1:1 채팅 진입 URL 예시
                .build();
    }
}