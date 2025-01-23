package com.aivle.platform.service;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Comment;
import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
import com.aivle.platform.dto.request.CommentRequestDto;
import com.aivle.platform.dto.response.CommentResponseDto;
import com.aivle.platform.exception.comment.CommentNotFoundException;
import com.aivle.platform.repository.CommentRepository;
import com.aivle.platform.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    public Comment createComment(CommentRequestDto request, Member member, Board board) {
        Comment comment = CommentRequestDto.toEntity(request);
        comment.setMember(member);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setMember(member);
        comment.setBoard(board);

        // parentCommentId가 null이 아니면 DB에서 부모 댓글을 찾아서 세팅
        if (request.getParentCommentId() != null) {
            Comment parent = getComment(request.getParentCommentId());
            comment.setParentComment(parent);
        }

        return commentRepository.save(comment);
    }


    public CommentResponseDto updateComment(Long commentId, CommentRequestDto request) {
        Comment comment = getComment(commentId);

        // 1) 기존 이미지 삭제 로직
        imageRepository.deleteAll(comment.getImages());
        comment.getImages().clear();

        // 2) comment 필드만 수정
        comment.setContent(request.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        // 3) 새로 입력받은 사진이 있다면 setImages
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<Image> images = request.getImageUrls().stream()
                    .map(url -> new Image(comment, url))
                    .collect(Collectors.toList());
            comment.setImages(images);
        }

        return CommentResponseDto.fromEntity(commentRepository.save(comment));
    }


}
