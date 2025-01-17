package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Comment;
import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.PoliceUnit;
import lombok.*;

import java.time.LocalDateTime;
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

    public static CommentResponseDto fromEntity(Comment comment, Member member, PoliceUnit policeUnit) {
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                member.getMemberName(),
                policeUnit.getDeptName(),
                policeUnit.getStationName(),
                policeUnit.getPoliceUnitName(),
                policeUnit.getPoliceUnitTypeDescription(),
                comment.getBoard().getBoardId(),
                comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null,
                comment.getImages() != null ? comment.getImages().stream()
                        .map(Image::getImageUrl).collect(Collectors.toList())
                        : List.of()
        );
    }

}