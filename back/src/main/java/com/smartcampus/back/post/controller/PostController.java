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
 * 게시글 관련 요청을 처리하는 REST 컨트롤러
 * 게시글 생성, 수정, 삭제, 조회, 검색 기능 제공
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     *
     * @param request 게시글 생성 요청 데이터 (제목, 내용, 작성자 ID, 공개 여부 등)
     * @param files 첨부파일 리스트 (선택적)
     * @return 생성된 게시글의 ID와 성공 메시지를 포함한 응답
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
     * 게시글 수정
     *
     * @param postId 수정할 게시글 ID
     * @param request 수정 요청 데이터 (제목, 내용, 공개 여부, 삭제할 파일 ID 목록 등)
     * @param newFiles 새로 추가할 첨부파일 목록 (선택적)
     * @return 수정 완료 메시지 포함 응답
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
     * 게시글 삭제
     *
     * @param postId 삭제할 게시글 ID
     * @param writerId 작성자 ID (권한 검증용)
     * @return 삭제 완료 메시지 포함 응답
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
     * 게시글 전체 목록 조회 (페이징 포함)
     *
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 게시글 목록 응답
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
     * 특정 게시글 상세 정보 조회
     *
     * @param postId 조회할 게시글 ID
     * @return 게시글 상세 정보 응답
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        PostDetailResponse response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 검색 (제목 또는 내용 기준)
     *
     * @param keyword 검색 키워드
     * @return 검색된 게시글 목록 응답
     */
    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String keyword) {
        List<PostResponse> responseList = postService.searchPosts(keyword);
        return ResponseEntity.ok(responseList);
    }

    /**
     * 게시글 첨부파일 단건 다운로드
     *
     * @param postId 게시글 ID
     * @param fileId 첨부파일 ID
     * @return 파일 리소스
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
     * 게시글 첨부파일 삭제 (작성자 검증 포함)
     *
     * @param postId 게시글 ID
     * @param fileId 첨부파일 ID
     * @param writerId 작성자 ID (검증용)
     * @return 삭제 성공 메시지
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
