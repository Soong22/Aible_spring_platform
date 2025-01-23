package com.aivle.platform.dto.request;

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

    private List<String> imageUrls;

    public static Comment toEntity(CommentRequestDto request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());

        // 보드는 컨트롤러에서 받기

        // 상위 게시판에서 유저 주입

        // 상위 댓글의 경우 서비스단에 생성

        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<Image> images = request.getImageUrls().stream()
                    .map(url -> new Image(comment, url))
                    .collect(Collectors.toList());

            comment.setImages(images);
        }

        return comment;
    }

}
