package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.PoliceUnit;
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


    public static BoardResponseDto fromEntity(Board board, Member member, PoliceUnit policeUnit) {
        return new BoardResponseDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getStatusDescription(),
                board.getViewCount(),
                member.getMemberName(),                           // 게시판 작성자의 Member 정보
                policeUnit.getDeptName(),                        // 게시판 작성자의 경찰청 정보
                policeUnit.getStationName(),                     // 게시판 작성자의 경찰서 정보
                policeUnit.getPoliceUnitName(),                  // 게시판 작성자의 지구대/파출소 정보
                policeUnit.getPoliceUnitTypeDescription(),       // 게시판 작성자의 지구대/파출소 유형

                // 댓글 매핑 - 댓글 작성자 정보 기반으로 매핑
                board.getComments() != null
                        ? board.getComments().stream()
                        .map(comment -> CommentResponseDto.fromEntity(
                                comment,
                                comment.getMember(),               // 댓글 작성자의 Member 정보
                                comment.getMember().getPoliceUnit()// 댓글 작성자의 PoliceUnit 정보
                        ))
                        .collect(Collectors.toList())
                        : List.of(),

                // 이미지 매핑
                board.getImages() != null
                        ? board.getImages().stream()
                        .map(Image::getImageUrl)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }


}
