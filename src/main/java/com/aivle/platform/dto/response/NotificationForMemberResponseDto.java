package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.type.PoliceUnitType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NotificationForMemberResponseDto {

    private Long memberId;
    private String policeUnitName; // 지구대/파출소명
    private String policeUnitType; // 문자열로 받고, 지구대/파출소 선택

    public NotificationForMemberResponseDto(Long memberId, String policeUnitName, String policeUnitType) {
        this.memberId = memberId;
        this.policeUnitName = policeUnitName;

        // ENUM을 한글로 변환
        this.policeUnitType = convertToKorean(policeUnitType);
    }

    // ENUM의 한글 설명을 반환하는 메서드
    private String convertToKorean(String policeUnitType) {
        try {
            return PoliceUnitType.valueOf(policeUnitType).getDescription(); // 한글 변환
        } catch (IllegalArgumentException e) {
            return "알 수 없음"; // 잘못된 값이 들어왔을 때 기본값 설정
        }
    }

    // ============ 엔티티 → DTO 변환 메서드 ============
    public static NotificationForMemberResponseDto fromEntity(Member member) {
        NotificationForMemberResponseDto response = new NotificationForMemberResponseDto();

        // 탈퇴자는 미리 제외시키기, 검색으로 찾기
        response.setMemberId(member.getMemberId());
        response.setPoliceUnitName(member.getPoliceUnit().getPoliceUnitName());
        response.setPoliceUnitType(member.getPoliceUnit().getPoliceUnitTypeDescription());

        return response;
    }


}
