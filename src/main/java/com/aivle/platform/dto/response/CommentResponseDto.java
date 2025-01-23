package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Comment;
import com.aivle.platform.domain.Image;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

    // 수정일의 경우 없으면 표시x
    private LocalDateTime updatedAt;

    private String memberName; // 회원이름
    private String deptName; // 소속경찰청명 - 선택사항
    private String stationName; // 소속경찰서명 - 선택사항
    private String policeUnitName; // 지구대/파출소명
    private String policeUnitType; // 문자열로 받고, 지구대/파출소 선택

    private Long boardId;
    private Long parentCommentId;

    private List<String> imageUrls;

    // ** 자식 댓글 리스트 필드(계층형 트리 구조를 위해 필요) **
    private List<CommentResponseDto> children = new ArrayList<>();


    public List<CommentResponseDto> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }


    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto response = new CommentResponseDto();

        response.setCommentId(comment.getCommentId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());

        response.setMemberName(comment.getMember().getMemberName());
        response.setDeptName(comment.getMember().getPoliceUnit().getDeptName());
        response.setStationName(comment.getMember().getPoliceUnit().getStationName());
        response.setPoliceUnitName(comment.getMember().getPoliceUnit().getPoliceUnitName());
        response.setPoliceUnitType(comment.getMember().getPoliceUnit().getPoliceUnitTypeDescription());

        response.setBoardId(comment.getBoard().getBoardId());

        response.setParentCommentId(
                comment.getParentComment() != null
                        ? comment.getParentComment().getCommentId()
                        : null
        );

        // 이미지 URL 리스트
        response.setImageUrls(
                comment.getImages() != null
                        ? comment.getImages().stream()
                        .map(Image::getImageUrl)
                        .collect(Collectors.toList())
                        : List.of()
        );

        // children 필드는 여기서는 아직 세팅할 수 없음 (빌드 로직에서 처리)
        // 트리 빌드 로직에서 response.getChildren().add(...) 방식으로 자식들을 세팅할 것임.

        return response;
    }

}