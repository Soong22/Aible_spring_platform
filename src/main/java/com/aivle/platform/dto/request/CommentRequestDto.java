package com.aivle.platform.dto.request;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Comment;
import com.aivle.platform.domain.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 1, max = 1000, message = "내용은 1자에서 1000자 사이여야 합니다.")
    private String content;

    @NotNull(message = "게시판 ID는 필수입니다.")
    private Long boardId;

    private Long parentCommentId;

    @Size(max = 5, message = "이미지는 최대 5개까지 첨부할 수 있습니다.")
    private List<String> imageUrls;

    public static Comment toEntity(CommentRequestDto request, Board board) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setBoard(board);

        // 상위 게시판에서 유저 주입

        // 상위 댓글의 경우 서비스단에 생성
        // static으로 아이디로 comment찾는거 생성 후 여기에 대입하기


        List<Image> images = request.getImageUrls().stream()
                .map(url -> new Image(comment, url))
                .collect(Collectors.toList());

        comment.setImages(images);

        return comment;
    }

}
