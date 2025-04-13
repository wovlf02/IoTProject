package com.smartcampus.back.post.controller;

import com.smartcampus.back.post.dto.post.*;
import com.smartcampus.back.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 관련 요청을 처리하는 REST 컨트롤러 클래스입니다.
 * - 게시글 생성, 수정, 삭제, 조회, 검색, 첨부파일 기능을 지원합니다.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글을 생성합니다.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostCreateResponse> createPost(
            @RequestPart("post") PostCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        PostCreateResponse response = postService.createPost(request, files);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글을 수정합니다.
     */
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostUpdateResponse> updatePost(
            @PathVariable Long postId,
            @RequestPart("post") PostUpdateRequest request,
            @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles
    ) {
        PostUpdateResponse response = postService.updatePost(postId, request, newFiles);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글을 삭제합니다. (작성자 검증 포함)
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDeleteResponse> deletePost(
            @PathVariable Long postId,
            @RequestParam("writerId") Long writerId
    ) {
        PostDeleteResponse response = postService.deletePost(postId, writerId);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 전체 목록을 페이징으로 조회합니다.
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponse> responseList = postService.getPostList(page, size);
        return ResponseEntity.ok(responseList);
    }

    /**
     * 게시글 상세 정보를 조회하고 조회수를 1 증가시킵니다.
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        PostDetailResponse response = postService.getPostDetailAndIncreaseView(postId);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글을 제목 또는 내용 기준으로 검색합니다.
     */
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String keyword) {
        List<PostResponse> responseList = postService.searchPosts(keyword);
        return ResponseEntity.ok(responseList);
    }

    /**
     * 게시글에 연결된 첨부파일을 단건 다운로드합니다.
     */
    @GetMapping("/{postId}/attachments/{fileId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long postId,
            @PathVariable Long fileId
    ) {
        Resource file = postService.loadFileAsResource(postId, fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * 게시글에 연결된 첨부파일을 삭제합니다. (작성자 검증 포함)
     */
    @DeleteMapping("/{postId}/attachments/{fileId}")
    public ResponseEntity<String> deleteAttachment(
            @PathVariable Long postId,
            @PathVariable Long fileId,
            @RequestParam("writerId") Long writerId
    ) {
        postService.deleteAttachment(postId, fileId, writerId);
        return ResponseEntity.ok("첨부파일이 성공적으로 삭제되었습니다.");
    }
}
