package com.aivle.platform.dto.response;

import com.aivle.platform.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String role;
    private String memberName;
    private String personPhone;
    private String officePhone;
    private String districtName;
    private LocalDateTime createdAt;

    // 엔티티 -> DTO 변환
    public static MemberResponseDto toDto(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getRole().name(),
                member.getMemberName(),
                member.getPersonPhone(),
                member.getOfficePhone(),
                member.getDistrictName(),
                member.getCreatedAt()
        );
    }
}
