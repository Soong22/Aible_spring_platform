package com.aivle.platform.dto.request;

import com.aivle.platform.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다.")
    private String password;

    @NotBlank(message = "사용자 이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "사용자 이름은 2자에서 50자 사이여야 합니다.")
    private String memberName;

    @NotBlank(message = "개인전화번호는 필수입니다.")
    private String personPhone;

    private String officePhone;

    @NotBlank(message = "관활부서는 필수입니다.")
    private String districtName;

    // DTO -> 엔티티 변환
    public static Member toEntity(MemberRequestDto request) {
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setMemberName(request.getMemberName());
        member.setPersonPhone(request.getPersonPhone());
        member.setOfficePhone(request.getOfficePhone());
        member.setDistrictName(request.getDistrictName());

        return member;
    }

}
