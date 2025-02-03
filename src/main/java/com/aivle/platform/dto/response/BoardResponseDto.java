package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.PoliceUnit;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // 수정일의 경우 없으면 표시x
    private LocalDateTime updatedAt;

    private String status; // enum 문자열로 받기

    private Integer viewCount; // 조회수

    private String memberName; // 회원이름
    private String deptName; // 소속경찰청명 - 선택사항
    private String stationName; // 소속경찰서명 - 선택사항
    private String policeUnitName; // 지구대/파출소명
    private String policeUnitType; // 문자열로 받고, 지구대/파출소 선택

    private List<CommentResponseDto> comments; // 이건 댓글리스폰스로 가지고오기

    private List<String> imageUrls;

    // ============ 엔티티 → DTO 변환 메서드 ============
    public static BoardResponseDto fromEntity(Board board) {
        BoardResponseDto response = new BoardResponseDto();

        // 1) 게시글 기본 정보 세팅
        response.setBoardId(board.getBoardId());
        response.setTitle(board.getTitle());
        response.setContent(board.getContent());
        response.setCreatedAt(board.getCreatedAt());
        response.setUpdatedAt(board.getUpdatedAt());
        response.setStatus(board.getStatusDescription());
        response.setViewCount(board.getViewCount());

        // 2) 작성자, 경찰청 정보
        Member member = board.getMember();
        response.setMemberName(member.getMemberName());

        if (member.getPoliceUnit() != null) {
            response.setDeptName(member.getPoliceUnit().getDeptName());
            response.setStationName(member.getPoliceUnit().getStationName());
            response.setPoliceUnitName(member.getPoliceUnit().getPoliceUnitName());
            response.setPoliceUnitType(member.getPoliceUnit().getPoliceUnitTypeDescription());
        } else {
            response.setDeptName("탈퇴자");
            response.setStationName("탈퇴자");
            response.setPoliceUnitName("탈퇴자");
            response.setPoliceUnitType("탈퇴자");
        }

        // 3) 댓글(Flat → 트리) 빌드
        if (board.getComments() != null) {
            // (1) Flat하게 DTO 변환
            List<CommentResponseDto> flatList = board.getComments().stream()
                    .map(CommentResponseDto::fromEntity)
                    .collect(Collectors.toList());

            // (2) 트리 구조로 변환
            List<CommentResponseDto> commentTree = buildCommentTree(flatList);

            // (3) dto에 계층 구조 댓글 세팅
            response.setComments(commentTree);

        } else {
            response.setComments(List.of());
        }

        // 4) 이미지 세팅
        if (board.getImages() != null) {
            List<String> imageUrls = board.getImages().stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());
            response.setImageUrls(imageUrls);
        } else {
            response.setImageUrls(List.of());
        }

        return response;
    }

    // ============ 댓글 리스트를 트리로 만들어주는 메서드 ============
    private static List<CommentResponseDto> buildCommentTree(List<CommentResponseDto> flatList) {
        // commentId → DTO 매핑
        Map<Long, CommentResponseDto> commentMap = new HashMap<>();
        for (CommentResponseDto responseMap : flatList) {
            commentMap.put(responseMap.getCommentId(), responseMap);
        }

        // 루트 댓글(= parentCommentId가 null) 리스트
        List<CommentResponseDto> rootComments = new ArrayList<>();

        // 모든 댓글을 돌면서, 부모-자식 연결
        for (CommentResponseDto responseList : flatList) {
            Long parentCommentId = responseList.getParentCommentId();

            if (parentCommentId == null) {
                // 부모가 없으므로 루트 댓글
                rootComments.add(responseList);
            } else {
                // 부모가 존재하면, parent의 children에 현재 댓글을 추가
                CommentResponseDto parent = commentMap.get(parentCommentId);
                if (parent != null) {
                    parent.getChildren().add(responseList);
                }
            }
        }

        return rootComments;
    }

}
