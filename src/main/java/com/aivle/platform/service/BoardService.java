package com.aivle.platform.service;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Status;
import com.aivle.platform.dto.request.BoardRequestDto;
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
    @Transactional
    public Board createBoard(BoardRequestDto request, Member member) {
        Board board = BoardRequestDto.toEntity(request);
        board.setMember(member);
        board.setCreatedAt(LocalDateTime.now());
        board.setStatus(Status.PENDING);

        return boardRepository.save(board);
    }

//    @Transactional(readOnly = true)
//    public





























}
