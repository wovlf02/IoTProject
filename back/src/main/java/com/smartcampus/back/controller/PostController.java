package com.smartcampus.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * PostController - 게시판 관련 컨트롤러
 *
 * 게시판 작성, 수정, 삭제, 조회, 검색, 추천, 신고 기능 포함
 * 댓글 및 대댓글 관련 기능 포함
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // ====================== 1. 게시글 관련 API ==========================

    /**
     * [게시글 작성]
     *
     * 사용자가 제목, 본문, 첨부파일을 포함하여 게시글을 작성할 수 있음
     * @param request 게시글 작성 요청 DTO
     * @param files 첨부파일 목록 (선택 사항)
     * @return 생성된 게시글 ID
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()") // 인증된 사용자만 접근 가능
    public ResponseEntity<Long> createPost(
            @RequestPart("post") PostRequest request,
            @RequestPart(value = "files", required = false)List<MultipartFile> files) {
        Long postId = postService.createPost(request, files);
        return ResponseEntity.ok(postId);
    }

    /**
     * [게시글 수정]
     *
     * 사용자가 자신이 작성한 게시글을 수정할 수 있음
     *
     * @param postId 게시글 ID
     * @param request 게시글 수정 요청 DTO
     * @param files 새로운 첨부파일 목록 (선택 사항)
     * @return 수정된 게시글 ID
     */
    @PutMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> updatePost(
            @PathVariable Long postId,
            @RequestPart("post") PostRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Long updatedPostId = postService.updatePost(postId, request, files);
        return ResponseEntity.ok(updatedPostId);
    }

    /**
     * [게시글 삭제]
     *
     * 사용자가 자신이 작성한 게시글을 삭제할 수 있음
     *
     * @param postId 삭제할 게시글 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

    /**
     * [게시글 목록 조회]
     *
     * 최신순 또는 추천순으로 게시글 목록을 조회
     *
     * @param sortType 정렬 기준 (latest: 최신순, popular: 추천순)
     * @return 게시글 목록 DTO
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(
            @RequestParam(defaultValue = "latest") String sortType
    ) {
        List<PostResponse> posts = postService.getPosts(sortType);
        return ResponseEntity.ok(posts);
    }

    /**
     * [게시글 상세 조회]
     *
     * 특정 게시글을 상세 조회
     *
     * @param postId 조회할 게시글 ID
     * @return 게시글 상세 정보 DTO
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetails(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostDetails(postId);
        return ResponseEntity.ok(postResponse);
    }

    /**
     * [게시글 검색]
     *
     * 제목을 기준으로 게시글 검색
     *
     * @param keyword 검색 키워드
     * @return 검색된 게시글 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String keyword) {
        List<PostResponse> posts = postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }

    // =========================== 2. 게시글 추천 API ==============================

    /**
     * [게시글 추천]
     *
     * 사용자가 특정 게시글에 대해 추천(좋아요) 버튼을 누름
     *
     * @param postId 추천할 게시글 ID
     * @return 추천 성공 메시지
     */
    @PostMapping("/{postId}/recommend")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> recommendPost(@PathVariable Long postId) {
        postService.recommendPost(postId);
        return ResponseEntity.ok("게시글이 추천되었습니다.");
    }

    // =========================== 3. 게시글 신고 API =============================

    /**
     * [게시글 신고]
     *
     * 사용자가 특정 게시글을 신고할 수 있음
     *
     * @param postId 신고할 게시글 ID
     * @param request 신고 요청 DTO
     * @return 신고 성공 메시지
     */
    @PostMapping("/{postId}/report")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> reportPost(
            @PathVariable Long postId,
            @RequestBody ReportRequest request
    ) {
        postService.reportPost(postId, request);
        return ResponseEntity.ok("게시글이 신고되었습니다.");
    }

    // ========================== 4. 댓글 및 대댓글 API ===========================

    /**
     * [댓글 작성]
     *
     * 특정 게시글에 대한 댓글 작성
     *
     * @param postId 댓글이 달릴 게시글 ID
     * @param commentRequest 댓글 요청 DTO
     * @return 생성된 댓글 ID
     */
    @PostMapping("/{postId}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        Long commentId = postService.addComment(postId, request);
        return ResponseEntity.ok(commentId);
    }

    /**
     * [댓글 삭제]
     *
     * 사용자가 자신의 댓글을 삭제할 수 있음
     *
     * @param commentId 삭제할 댓글 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

    /**
     * [대댓글 작성]
     *
     * 특정 댓글에 대한 대댓글 작성
     *
     * @param commentId 대댓글이 달릴 댓글 ID
     * @param request 대댓글 요청 DTO
     * @return 생성된 대댓글 ID
     */
    @PostMapping("/comments/{commentId}/replies")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> addReply(
            @PathVariable Long commentId,
            @RequestBody ReplyRequest request
    ) {
        Long replyId = postService.addReply(commentId, request);
        return ResponseEntity.ok(replyId);
    }

    /**
     * [대댓글 삭제]
     *
     * 사용자가 자신의 대댓글을 삭제할 수 있음
     *
     * @param replyId 삭제할 대댓글 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/comments/replies/{replyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId) {
        postService.deleteReply(replyId);
        return ResponseEntity.ok("대댓글이 삭제되었습니다.")
    }
}
