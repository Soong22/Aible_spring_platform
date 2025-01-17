package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String role;
    private String memberName;
    private String personPhone;
    private String officePhone;
    private LocalDateTime createdAt;
    private Long policeUnitId; // 사용자로부터 선택받은 police_unit_id를 받음

    // 엔티티 -> DTO 변환
    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getRoleDescription(),
                member.getMemberName(),
                member.getPersonPhone(),
                member.getOfficePhone(),
                member.getCreatedAt(),
                member.getPoliceUnit() != null ? member.getPoliceUnit().getPoliceUnitId() : null // 경찰서 ID 반환
        );
    }

}
