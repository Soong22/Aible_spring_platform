package com.aivle.platform.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

}