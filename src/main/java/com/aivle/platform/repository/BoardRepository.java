package com.aivle.platform.repository;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Notification;
import com.aivle.platform.domain.type.ReadStatus;
import com.aivle.platform.domain.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 게시글 상태에 따른 알림 불러오기
    Page<Board> findAllByStatus(Status status, Pageable pageable);

    Page<Board> findAllByStatusIn(List<Status> statuses, Pageable pageable);

}
