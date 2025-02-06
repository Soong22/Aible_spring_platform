package com.aivle.platform.dto.request;

import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 1, max = 10000, message = "내용은 1자에서 10000자 사이여야 합니다.")
    private String content;

    private List<String> imageUrls;

    public static Notification toEntity(NotificationRequestDto request) {
        Notification notification = new Notification();
        notification.setContent(request.getContent());

        // 이미지가 있는 경우에만 처리
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<Image> images = request.getImageUrls().stream()
                    .map(url -> new Image(notification, url))
                    .collect(Collectors.toList());
            notification.setImages(images);
        }

        return notification;
    }

}
