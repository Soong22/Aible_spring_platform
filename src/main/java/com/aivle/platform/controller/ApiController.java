package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.PoliceUnit;
import com.aivle.platform.domain.type.Role;
import com.aivle.platform.dto.response.MemberResponseDto;
import com.aivle.platform.dto.response.NotificationForMemberResponseDto;
import com.aivle.platform.service.MemberService;
import com.aivle.platform.service.PoliceUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final MemberService memberService;
    private final PoliceUnitService policeUnitService;

    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable String email) {
        return memberService.existsByEmail(email);
    }

    // 지구대/파출소 사용 여부 확인
    @GetMapping("/check-police-unit/{policeUnitId}")
    public ResponseEntity<Boolean> checkPoliceUnit(@PathVariable Long policeUnitId) {
        boolean isPoliceUnitUsed = memberService.checkPoliceUnitUsed(policeUnitId);
        return ResponseEntity.ok(isPoliceUnitUsed);
    }

    @GetMapping("/check-status/{email}")
    public String checkStatus(@PathVariable String email) {
        Member member = memberService.getMemberEmail(email);
        if (member == null) {
            return "NOT_FOUND"; // 사용자 없음
        } else if (member.getRole() == Role.WITHDRAWN) {
            return "WITHDRAWN"; // 탈퇴자
        } else {
            return "ACTIVE"; // 정상 사용자
        }
    }

    // 광역청 목록 가져오기
    @GetMapping("/police-units/departments")
    public List<String> getDepartments() {
        return policeUnitService.getDepartments();
    }

    // 특정 광역청에 속한 경찰서 목록 가져오기
    @GetMapping("/police-units/stations")
    public List<String> getStations(@RequestParam String deptName) {
        return policeUnitService.getStations(deptName);
    }

    // 특정 경찰서에 속한 지구대/파출소 목록 가져오기
    @GetMapping("/police-units/units")
    public List<PoliceUnit> getUnits(@RequestParam String deptName, @RequestParam String stationName) {
        return policeUnitService.getUnits(deptName, stationName);
    }

    @GetMapping("/police-units/member/{memberId}")
    public ResponseEntity<PoliceUnit> getPoliceUnitByMemberId(@PathVariable Long memberId) {
        MemberResponseDto response = memberService.getMemberById(memberId);

        if (response.getPoliceUnitId() != null) {
            PoliceUnit policeUnit = policeUnitService.getPoliceUnitById(response.getPoliceUnitId());
            return ResponseEntity.ok(policeUnit);
        } else {
            return ResponseEntity.noContent().build(); // 🔥 404 → 204 변경
        }
    }

    // 탈퇴자가 아닌 유저 목록 반환 (관리자가 선택할 대상)
    @GetMapping("/active")
    public ResponseEntity<List<NotificationForMemberResponseDto>> getActiveMembers() {
        List<NotificationForMemberResponseDto> members = memberService.getActiveMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkMemberExists(
            @RequestParam String email,
            @RequestParam String memberName) {
        boolean response = memberService.checkEmail(email, memberName);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-email-pwd")
    public ResponseEntity<Boolean> checkEmailExists(
            @RequestParam String email) {
        boolean response = memberService.existsByEmail(email);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-pwd")
    public ResponseEntity<Map<String, Object>> changePassword(
            @RequestParam String email,
            @RequestParam String newPassword) {

        boolean success = memberService.changePassword(email, newPassword);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        if (success) {
            response.put("message", "비밀번호 변경 성공");
        } else {
            response.put("message", "비밀번호 변경 실패: 해당 이메일을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(response);
    }

}
