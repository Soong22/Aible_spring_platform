package com.aivle.platform.service;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Status;
import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.exception.board.BoardCreationFailedException;
import com.aivle.platform.repository.BoardRepository;
import com.aivle.platform.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // CRUD

    // 게시판 작성
    public Board createBoard(BoardRequestDto request, Member member) {
        // 멤버의 경우 멤버 서비스에서 auth로 찾는 메소드 생성후  서비스에 주입

        try{
            Board board = BoardRequestDto.toEntity(request);
            board.setCreatedAt(LocalDateTime.now());
            // 일반 공지사항은 GENERAL로 설정
            board.setStatus(Status.PENDING); // 관리자가 게시판 확인시 COMPLETE로 변경되게
            board.setMember(member);

            return boardRepository.save(board);
        } catch (Exception e) {
            throw new BoardCreationFailedException("게시판 생성에 실패하였습니다: " + e.getMessage(), e.getCause());
        }

    }

    // 일반 게시판 작성

}
