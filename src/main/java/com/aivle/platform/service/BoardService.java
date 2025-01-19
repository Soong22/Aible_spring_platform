package com.aivle.platform.service;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Status;
import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.dto.response.BoardResponseDto;
import com.aivle.platform.exception.board.BoardNotFoundException;
import com.aivle.platform.exception.image.FileSaveException;
import com.aivle.platform.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 기능
    public String saveFileAndGetUrl(MultipartFile file) {
        // 프로젝트 루트 디렉토리 내의 static/uploads 디렉토리에 저장
        String saveDir = new File("src/main/resources/static/uploads").getAbsolutePath() + "/";

        // Ensure the save directory exists
        File directory = new File(saveDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File savedFile = new File(saveDir + filename);
            file.transferTo(savedFile);
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new FileSaveException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

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

    public BoardResponseDto getBoardById(Long boardId) {
        Board board = getBoard(boardId);
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);

        return BoardResponseDto.fromEntity(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardResponseDto> getAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);

        return boards.map(BoardResponseDto::fromEntity);
    }


}
