package com.aivle.platform.service;

import com.aivle.platform.domain.*;
import com.aivle.platform.dto.request.CommentRequestDto;
import com.aivle.platform.dto.response.CommentResponseDto;
import com.aivle.platform.exception.comment.CommentDeletionFailedException;
import com.aivle.platform.exception.comment.CommentNotFoundException;
import com.aivle.platform.exception.comment.CommentPermissionDeniedException;
import com.aivle.platform.exception.comment.CommentUpdateFailedException;
import com.aivle.platform.repository.CommentRepository;
import com.aivle.platform.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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


    public CommentResponseDto updateComment(Long commentId, CommentRequestDto request, Member member) {
        Comment comment = getComment(commentId);

        if (!Objects.equals(comment.getMember().getMemberId(), member.getMemberId()) && member.getRole() != Role.ADMIN) {
            throw new CommentPermissionDeniedException("댓글에 대한 권한이 없습니다: " + member.getEmail());
        }

        try{
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
        } catch (Exception e) {
            throw new CommentUpdateFailedException("댓글 수정에 실패하였습니다: " + e.getMessage(), e.getCause());
        }
    }

    public void deleteComment(Long commentId, Member member) {
        Comment comment = getComment(commentId);

        if (!Objects.equals(comment.getMember().getMemberId(), member.getMemberId()) && member.getRole() != Role.ADMIN) {
            throw new CommentPermissionDeniedException("댓글에 대한 권한이 없습니다: " + member.getEmail());
        }

        try{
            // 1) 기존 이미지 삭제 로직
            imageRepository.deleteAll(comment.getImages());
            comment.getImages().clear();
            comment.setContent("삭제된 댓글입니다.");
            // 나중에 생성일, 수정일 모두 널 처리해서 프론트 해서 널이면 표시안하게 하기

            commentRepository.save(comment);
        } catch (Exception e) {
            throw new CommentDeletionFailedException("댓글 삭제에 실패하였습니다: " + e.getMessage(), e.getCause());
        }


    }

}
