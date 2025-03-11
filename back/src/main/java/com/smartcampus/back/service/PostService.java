package com.smartcampus.back.service;

import com.smartcampus.back.entity.Post;
import com.smartcampus.back.entity.PostAttachment;
import com.smartcampus.back.entity.PostLike;
import com.smartcampus.back.entity.User;
import com.smartcampus.back.repository.PostAttachmentRepository;
import com.smartcampus.back.repository.PostLikeRepository;
import com.smartcampus.back.repository.PostRepository;
import com.smartcampus.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 게시글 관리 서비스
 * 게시글 CRUD, 추천, 파일 관리 등을 처리
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostAttachmentRepository postAttachmentRepository;
    private final UserRepository userRepository;

    // ==================== 1. 게시글 CRUD ========================

    /**
     * 게시글 작성
     * @param request게시글 작성 요청 DTO
     * @param files 첨부파일 (선택 사항)
     * @return 작성된 게시글 정보
     */
    public PostResponse createPost(PostRequest request, List<MultipartFile> files) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepository.save(post);

        // 파일 업로드
        if(files != null && !files.isEmpty()) {
            saveAttachments(post, files);
        }

        return new PostResponse(post);
    }

    /**
     * 게시글 목록 조회 (페이지네이션 적용)
     * @return 게시글 목록 (최신순 정렬)
     */
    public List<PostResponse> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostResponse::new).collect(Collectors.toList());
    }

    /**
     * 게시글 상세 조회
     * @param postId 조회할 게시글 ID
     * @return 게시글 상세 정보
     */
    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return new PostResponse(post);
    }

    /**
     * 게시글 수정
     * @param postId 수정할 게시글 ID
     * @param request 수정할 게시글 정보
     * @return 수정된 게시글 정보
     */
    public PostResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);

        return new PostResponse(post);
    }

    /**
     * 게시글 삭제
     * @param postId 삭제할 게시글 ID
     * @return 삭제 성공 메시지
     */
    public String deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        postRepository.delete(post);

        return "게시글이 삭제되었습니다.";
    }

    // ==================== 2. 게시글 추천 기능 ========================

    /**
     * 게시글 추천 추가
     * @param postId 추천할 게시글 ID
     * @param userId 추천한 사용자 ID
     */
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 이미 추천을 한 상태일 경우 중복 방지
        if(postLikeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalArgumentException("이미 추천된 게시글입니다.");
        }

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();

        postLikeRepository.save(postLike);
    }

    /**
     * 게시글 추천 취소
     * @param postId 취소할 게시글 ID
     * @param userId 취소하는 사용자 ID
     */
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Optional<PostLike> postLike = postLikeRepository.findByUserAndPost(user, post);
        postLike.ifPresent(postLikeRepository::delete);
    }

    // ==================== 3. 게시글 첨부파일 관리 ========================

    /**
     * 첨부파일 저장
     * @param post 게시글 객체
     * @param files 첨부파일 목록
     */
    private void saveAttachments(Post post, List<MultipartFile> files) {
        for(MultipartFile file : files) {
            try {
                PostAttachment attachment = PostAttachment.builder()
                        .post(post)
                        .fileData(file.getBytes())
                        .fileType(file.getContentType())
                        .build();

                postAttachmentRepository.save(attachment);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생: ", e.getMessage());
            }
        }
    }

    /**
     * 첨부파일 다운로드
     * @param attachmentId 다운로드할 파일 ID
     * @return 파일 데이터
     */
    public byte[] downloadAttachment(Long attachmentId) {
        PostAttachment attachment = postAttachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
        return attachment.getFileData();
    }

    /**
     * 첨부파일 삭제
     * @param attachmentId 삭제할 파일 ID
     * @return 삭제 성공 메시지
     */
    public String deleteAttachment(Long attachmentId) {
        PostAttachment attachment = postAttachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
        postAttachmentRepository.delete(attachment);
        return "첨부파일이 삭제되었습니다.";
    }

}
