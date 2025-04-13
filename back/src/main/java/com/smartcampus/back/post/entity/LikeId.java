package com.smartcampus.back.post.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Like 엔티티의 복합 키 클래스입니다.
 * 사용자 ID(userId)와 좋아요 대상 ID(postId, commentId, replyId)의 조합으로 구성됩니다.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {

    private Long userId;     // 좋아요를 누른 사용자

    private Long postId;     // 게시글 대상 (nullable)

    private Long commentId;  // 댓글 대상 (nullable)

    private Long replyId;    // 대댓글 대상 (nullable)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeId)) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(userId, likeId.userId)
                && Objects.equals(postId, likeId.postId)
                && Objects.equals(commentId, likeId.commentId)
                && Objects.equals(replyId, likeId.replyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId, commentId, replyId);
    }
}
