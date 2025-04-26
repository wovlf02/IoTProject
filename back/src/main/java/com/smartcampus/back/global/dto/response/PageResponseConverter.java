package com.smartcampus.back.global.dto.response;

import com.smartcampus.back.chat.dto.response.ChatMessageResponse;
import com.smartcampus.back.global.dto.response.CursorPageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PageResponseConverter
 * <p>
 * JPA 페이징 결과인 Page 객체를 CursorPageResponse로 변환하는 유틸리티 클래스입니다.
 * </p>
 */
public class PageResponseConverter {

    /**
     * Page 객체를 CursorPageResponse로 변환
     *
     * @param page JPA 페이징 결과
     * @param <T>  데이터 타입 (예: ChatMessageResponse)
     * @return 커서 기반 페이징 응답 DTO
     */
    public static <T> CursorPageResponse<T> toCursorPageResponse(Page<T> page) {
        List<T> content = page.getContent();
        Long nextCursor = content.isEmpty() ? null : getNextCursor(content, page.getSize());
        CursorPageResponse.CursorResult cursorResult = CursorPageResponse.CursorResult.of(nextCursor, page.getSize());
        return CursorPageResponse.of(content, cursorResult);
    }

    /**
     * 커서로 사용할 다음 페이지의 커서 ID를 구하는 로직
     * (보통 가장 마지막 ID 또는 timestamp로 처리)
     *
     * @param content 페이징된 데이터
     * @param size    페이지 크기
     * @return 다음 커서 ID
     */
    private static Long getNextCursor(List<?> content, int size) {
        // 페이징된 목록의 마지막 요소를 커서로 설정
        Object lastElement = content.get(content.size() - 1);
        if (lastElement instanceof ChatMessageResponse) {
            ChatMessageResponse messageResponse = (ChatMessageResponse) lastElement;
            return messageResponse.getId();
        }
        // 커서 추출 로직을 필요에 맞게 확장
        return null;
    }
}
