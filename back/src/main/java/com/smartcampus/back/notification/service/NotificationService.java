package com.smartcampus.back.notification.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import com.smartcampus.back.notification.dto.request.NotificationSendRequest;
import com.smartcampus.back.notification.dto.response.NotificationListResponse;
import com.smartcampus.back.notification.dto.response.NotificationResponse;
import com.smartcampus.back.notification.entity.Notification;
import com.smartcampus.back.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 알림 저장, 조회, 읽음 처리 등의 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    /**
     * 알림 저장
     *
     * @param request 알림 전송 요청 DTO
     */
    @Transactional
    public void saveNotification(NotificationSendRequest request) {
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Notification notification = Notification.create(
                receiver,
                request.getTitle(),
                request.getBody(),
                request.getType(),
                request.getReferenceId(),
                request.getClickAction() // referenceUrl 로 사용
        );

        notificationRepository.save(notification);
    }

    /**
     * 사용자 알림 목록 조회
     *
     * @param userId 사용자 ID
     * @return 알림 리스트 응답 DTO
     */
    @Transactional(readOnly = true)
    public NotificationListResponse getUserNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(user);

        List<NotificationResponse> responseList = notifications.stream()
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .body(n.getBody())
                        .createdAt(n.getCreatedAt())
                        .isRead(n.isRead())
                        .type(n.getType())
                        .referenceId(n.getReferenceId())
                        .referenceUrl(n.getReferenceUrl())
                        .build())
                .collect(Collectors.toList());

        return NotificationListResponse.from(responseList);
    }

    /**
     * 단일 알림 조회
     *
     * @param notificationId 알림 ID
     * @return 알림 상세 응답 DTO
     */
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(Long notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "알림을 찾을 수 없습니다."));

        return NotificationResponse.builder()
                .id(n.getId())
                .title(n.getTitle())
                .body(n.getBody())
                .createdAt(n.getCreatedAt())
                .isRead(n.isRead())
                .type(n.getType())
                .referenceId(n.getReferenceId())
                .referenceUrl(n.getReferenceUrl())
                .build();
    }

    /**
     * 알림을 읽음 처리합니다.
     *
     * @param notificationId 알림 ID
     */
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "알림을 찾을 수 없습니다."));

        notification.markAsRead();
    }
}
