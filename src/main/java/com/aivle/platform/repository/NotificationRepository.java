package com.aivle.platform.repository;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Notification;
import com.aivle.platform.domain.type.ReadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 관리자가 상태에 따른 알림 불러오기
    Page<Notification> findAllByReadStatus(ReadStatus readStatus, Pageable pageable);

    // 처리완료가 아닌 알림 불러오기
    Page<Notification> findAllByReceiverAndReadStatusNot(Member receiver, ReadStatus readStatus, Pageable pageable);

    // 처리완료인 알림 불러오기
    Page<Notification> findAllByReceiverAndReadStatus(Member receiver, ReadStatus readStatus, Pageable pageable);

}
