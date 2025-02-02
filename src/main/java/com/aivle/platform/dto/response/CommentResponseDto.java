package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Comment;
import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
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

    private Long memberId; // 멤버 아이디
    private String memberRole; // 멤버상태, 댓글에서 탈퇴자 표시를 위함
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

        // 2) 작성자, 경찰청 정보
        Member member = comment.getMember();

        response.setMemberId(member.getMemberId());
        response.setMemberRole(member.getRoleDescription());
        response.setMemberName(comment.getMember().getMemberName());

        if (member.getPoliceUnit() != null) {
            response.setDeptName(member.getPoliceUnit().getDeptName());
            response.setStationName(member.getPoliceUnit().getStationName());
            response.setPoliceUnitName(member.getPoliceUnit().getPoliceUnitName());
            response.setPoliceUnitType(member.getPoliceUnit().getPoliceUnitTypeDescription());
        } else {
            response.setDeptName("알 수 없음");
            response.setStationName("알 수 없음");
            response.setPoliceUnitName("알 수 없음");
            response.setPoliceUnitType("알 수 없음");
        }

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