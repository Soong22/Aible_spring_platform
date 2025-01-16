package com.aivle.platform.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Integer viewCount;

    private String memberName; // 회원이름
    private String deptName; // 소속경찰청명 - 선택사항
    private String stationName; // 소속경찰서명 - 선택사항
    private String policeUnitName; // 지구대/파출소명
    private String policeUnitType; // 문자열로 받고, 지구대/파출소 선택

    private List<CommentResponseDto> comments = new ArrayList<>(); // 이건 댓글리스폰스로 가지고오기

    private List<String> imageUrls;

}
