package com.aivle.platform.service;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Notification;
import com.aivle.platform.domain.type.ReadStatus;
import com.aivle.platform.dto.request.NotificationRequestDto;
import com.aivle.platform.dto.response.NotificationResponseDto;
import com.aivle.platform.exception.notification.NotificationNotFoundException;
import com.aivle.platform.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional(readOnly = true)
    public Notification getNotification(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);
    }

    public Notification createNotification(NotificationRequestDto request,
                                           Member sender, Member receiver) {
        Notification notification = NotificationRequestDto.toEntity(request);
        notification.setSender(sender);
        notification.setReceiver(receiver);

        notification.setCreatedAt(LocalDateTime.now());
        notification.setReadStatus(ReadStatus.UNREAD);

        notification = notificationRepository.save(notification);

        // 알림이 저장된 후 수신자에게 실시간 푸쉬 알림 전송
        sendPushNotification(notification);

        return notification;
    }

    // 실시간 푸쉬 알림 전송 메서드
    private void sendPushNotification(Notification notification) {
        // DTO 변환 (필요한 경우)
        NotificationResponseDto dto = NotificationResponseDto.fromEntity(notification);
        log.info("WebSocket으로 전송되는 알림: {}", dto);
        // 예시: 수신자의 username(또는 식별자)를 사용해 개별 메시지를 보냅니다.
        // "/user/queue/notifications" 경로로 전송하면, 클라이언트에서 해당 경로를 구독하게 됩니다.
        messagingTemplate.convertAndSendToUser(
                notification.getReceiver().getEmail(), // 수신자의 고유 아이디(예: email)
                "/queue/notifications",
                dto
        );
    }

    @Transactional(readOnly = true)
    public NotificationResponseDto getNotificationById(Long notificationId) {
        return NotificationResponseDto.fromEntity(getNotification(notificationId));
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getAllNotifications(Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findAll(pageable);

        return notifications.map(NotificationResponseDto::fromEntity);
    }

    // 처리완료 아닌거 불러오기
    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getAllByReceiverAndReadStatusNot(Member receiver, Pageable pageable) {
        Page<Notification> notifications = notificationRepository
                .findAllByReceiverAndReadStatusNot(receiver, ReadStatus.COMPLETED, pageable);

        return notifications.map(NotificationResponseDto::fromEntity);
    }

    // 원하는 상태의 알림 불러오기
    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getAllByReceiverAndReadStatus(Member receiver,
                                                                       ReadStatus readStatus, Pageable pageable) {
        Page<Notification> notifications = notificationRepository
                .findAllByReceiverAndReadStatus(receiver, readStatus, pageable);

        return notifications.map(NotificationResponseDto::fromEntity);
    }

    // 관리자가 원하는 상태의 알림 불러오기
    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getAllByReadStatus(ReadStatus readStatus, Pageable pageable) {
        Page<Notification> notifications = notificationRepository
                .findAllByReadStatus(readStatus, pageable);

        return notifications.map(NotificationResponseDto::fromEntity);
    }

    public void changeNotificationReadStatus(Long notificationId) {
        Notification notification = getNotification(notificationId);
        notification.setReadStatus(ReadStatus.READ);
        notificationRepository.save(notification);
    }

    public void setNotificationComplete(Long notificationId) {
        Notification notification = getNotification(notificationId);
        notification.setReadStatus(ReadStatus.COMPLETED);
        notificationRepository.save(notification);
    }



}
