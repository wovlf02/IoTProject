package com.smartcampus.back.entity.community;

import com.smartcampus.back.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "BLOCKS",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_BLOCK_USER_POST_COMMENT_REPLY",
                columnNames = {"USER_ID", "POST_ID", "COMMENT_ID", "REPLY_ID"}
        )
)
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "block_seq_generator")
    @SequenceGenerator(name = "block_seq_generator", sequenceName = "BLOCK_SEQ", allocationSize = 1)
    private Long id;

    /**
     * 차단을 수행한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * 차단된 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    /**
     * 차단된 댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    /**
     * 차단된 대댓글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;
}