package com.aivle.platform.service;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Notification;
import com.aivle.platform.domain.type.ReadStatus;
import com.aivle.platform.dto.request.NotificationRequestDto;
import com.aivle.platform.dto.response.NotificationResponseDto;
import com.aivle.platform.exception.notification.NotificationNotFoundException;
import com.aivle.platform.repository.ImageRepository;
import com.aivle.platform.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ImageRepository imageRepository;

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

        return notificationRepository.save(notification);
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



}
