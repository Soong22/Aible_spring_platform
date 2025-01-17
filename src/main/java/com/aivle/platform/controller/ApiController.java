package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.PoliceUnit;
import com.aivle.platform.domain.Role;
import com.aivle.platform.dto.response.MemberResponseDto;
import com.aivle.platform.service.MemberService;
import com.aivle.platform.service.PoliceUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        PoliceUnit policeUnit = policeUnitService.getPoliceUnitById(response.getPoliceUnitId());

        if (policeUnit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(policeUnit);
    }

}
