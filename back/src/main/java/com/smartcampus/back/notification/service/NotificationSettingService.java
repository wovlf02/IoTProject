package com.smartcampus.back.notification.service;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.global.util.SecurityUtil;
import com.smartcampus.back.notification.dto.request.NotificationSettingRequest;
import com.smartcampus.back.notification.dto.response.NotificationSettingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * NotificationSettingService
 * <p>
 * 사용자 알림 수신 설정을 조회하고 변경하는 비즈니스 로직을 담당하는 서비스 클래스입니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationSettingService {

    /**
     * 로그인 사용자의 알림 설정을 조회합니다.
     *
     * @return 알림 설정 응답 객체
     */
    public NotificationSettingResponse getNotificationSettings() {
        User user = SecurityUtil.getCurrentUser();

        return NotificationSettingResponse.builder()
                .commentNotificationEnabled(user.getCommentNotificationEnabled())
                .replyNotificationEnabled(user.getReplyNotificationEnabled())
                .friendNotificationEnabled(user.getFriendNotificationEnabled())
                .chatNotificationEnabled(user.getChatNotificationEnabled())
                .nextClassNotificationEnabled(user.getNextClassNotificationEnabled())
                .lateWarningNotificationEnabled(user.getLateWarningNotificationEnabled())
                .build();
    }

    /**
     * 로그인 사용자의 알림 설정을 변경합니다.
     *
     * @param request 변경할 알림 설정 요청 객체
     */
    @Transactional
    public void updateNotificationSettings(NotificationSettingRequest request) {
        User user = SecurityUtil.getCurrentUser();

        user.setCommentNotificationEnabled(request.getCommentNotificationEnabled());
        user.setReplyNotificationEnabled(request.getReplyNotificationEnabled());
        user.setFriendNotificationEnabled(request.getFriendNotificationEnabled());
        user.setChatNotificationEnabled(request.getChatNotificationEnabled());
        user.setNextClassNotificationEnabled(request.getNextClassNotificationEnabled());
        user.setLateWarningNotificationEnabled(request.getLateWarningNotificationEnabled());
    }
}
