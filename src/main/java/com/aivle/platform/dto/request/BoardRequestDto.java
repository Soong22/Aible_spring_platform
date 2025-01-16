package com.aivle.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 50, message = "제목은 1자에서 50자 사이여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 1, max = 10000, message = "내용은 1자에서 10000자 사이여야 합니다.")
    private String content;

    @Size(max = 5, message = "이미지는 최대 5개까지 첨부할 수 있습니다.")
    private List<String> imageUrls;

}
