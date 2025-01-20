package com.aivle.platform.service;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Status;
import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.dto.response.BoardResponseDto;
import com.aivle.platform.exception.board.BoardNotFoundException;
import com.aivle.platform.exception.board.BoardUpdateFailedException;
import com.aivle.platform.exception.image.FileSaveException;
import com.aivle.platform.repository.BoardRepository;
import com.aivle.platform.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    // 파일 저장 및 URL 반환
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

    public void deleteExistingImages(Board board) {
        // 저장 디렉토리의 루트 경로 (saveFileAndGetUrl과 동일해야 함)
        String saveDir = new File("src/main/resources/static/uploads").getAbsolutePath() + "/";

        for (Image image : board.getImages()) {
            try {
                // 파일 절대 경로 생성
                String absolutePath = saveDir + image.getImageUrl().replace("/uploads/", "");
                Path filePath = Paths.get(absolutePath);

                // 파일 삭제
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    log.info("파일 삭제 성공: {}", filePath);
                } else {
                    log.warn("파일이 존재하지 않습니다: {}", filePath);
                }
            } catch (IOException e) {
                // 파일 삭제 실패 로그
                log.error("파일 삭제 실패: {}", image.getImageUrl(), e);
            }
        }

        // 데이터베이스에서 이미지 정보 삭제
        imageRepository.deleteAll(board.getImages());
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

    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto request){
        try {
            Board board = getBoard(boardId);

            deleteExistingImages(board);

//            log.error("request: {}", request);

            Board newBoard = BoardRequestDto.toEntity(request);

            board.setTitle(newBoard.getTitle());

            board.setContent(newBoard.getContent());

//            log.error("newBoard이미지: {}", newBoard.getImages());
            board.setImages(newBoard.getImages());

//            log.error("board이미지: {}", board.getImages());

            board.setUpdatedAt(LocalDateTime.now());

            return BoardResponseDto.fromEntity(boardRepository.save(board));
        } catch (Exception e) {
            throw new BoardUpdateFailedException("게시판 수정에 실패하였습니다: " + e.getMessage(), e.getCause());
        }
    }

    public void deleteBoard(Long boardId) {
        boardRepository.delete(getBoard(boardId));
    }



























}
