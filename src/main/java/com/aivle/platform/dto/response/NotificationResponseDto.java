package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Image;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Notification;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {

    private Long notificationId;
    private String content;
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    private String readStatus;

    // 송신자
    private String senderName; // 회원이름

    private String senderDeptName; // 소속경찰청명 - 선택사항
    private String senderStationName; // 소속경찰서명 - 선택사항
    private String senderPoliceUnitName; // 지구대/파출소명
    private String senderPoliceUnitType; // 문자열로 받고, 지구대/파출소 선택

    // 수신자
    private String receiverName; // 회원이름

    private String receiverDeptName; // 소속경찰청명 - 선택사항
    private String receiverStationName; // 소속경찰서명 - 선택사항
    private String receiverPoliceUnitName; // 지구대/파출소명
    private String receiverPoliceUnitType; // 문자열로 받고, 지구대/파출소 선택

    private List<String> imageUrls;


    // ============ 엔티티 → DTO 변환 메서드 ============
    public static NotificationResponseDto fromEntity(Notification notification) {
        NotificationResponseDto response = new NotificationResponseDto();

        response.setNotificationId(notification.getNotificationId());
        response.setContent(notification.getContent());
        response.setCreatedAt(notification.getCreatedAt());
        response.setReadAt(notification.getReadAt());

        response.setReadStatus(notification.getReadStatusDescription());

        Member sender = notification.getSender();
        response.setSenderName(sender.getMemberName());

        if (sender.getPoliceUnit() != null) {
            response.setSenderDeptName(sender.getPoliceUnit().getDeptName());
            response.setSenderStationName(sender.getPoliceUnit().getStationName());
            response.setSenderPoliceUnitName(sender.getPoliceUnit().getPoliceUnitName());
            response.setSenderPoliceUnitType(sender.getPoliceUnit().getPoliceUnitTypeDescription());
        } else {
            response.setSenderDeptName("탈퇴자");
            response.setSenderStationName("탈퇴자");
            response.setSenderPoliceUnitName("탈퇴자");
            response.setSenderPoliceUnitType("탈퇴자");
        }

        Member receiver = notification.getReceiver();
        response.setReceiverName(receiver.getMemberName());

        if (receiver.getPoliceUnit() != null) {
            response.setReceiverDeptName(receiver.getPoliceUnit().getDeptName());
            response.setReceiverStationName(receiver.getPoliceUnit().getStationName());
            response.setReceiverPoliceUnitName(receiver.getPoliceUnit().getPoliceUnitName());
            response.setReceiverPoliceUnitType(receiver.getPoliceUnit().getPoliceUnitTypeDescription());
        } else {
            response.setReceiverDeptName("탈퇴자");
            response.setReceiverStationName("탈퇴자");
            response.setReceiverPoliceUnitName("탈퇴자");
            response.setReceiverPoliceUnitType("탈퇴자");
        }

        // 이미지 세팅
        if (notification.getImages() != null) {
            List<String> imageUrls = notification.getImages().stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());
            response.setImageUrls(imageUrls);
        } else {
            response.setImageUrls(List.of());
        }

        return response;
    }

}
