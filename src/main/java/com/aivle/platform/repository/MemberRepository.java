package com.aivle.platform.repository;

import com.aivle.platform.domain.Member;
import com.aivle.platform.dto.response.NotificationForMemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberNameAndPersonPhone(String memberName, String personPhone);

    // 지구대/파출소 사용 여부 확인
    boolean existsByPoliceUnit_PoliceUnitId(Long policeUnitId);

    // 알림 작성한 유저데이터, 탈퇴자, 관리자는 제외함
    @Query("SELECT new com.aivle.platform.dto.response.NotificationForMemberResponseDto(" +
            "m.memberId, m.memberName, p.policeUnitName, CAST(p.policeUnitType AS string)) " +
            "FROM Member m " +
            "JOIN m.policeUnit p " +
            "WHERE m.role <> 'WITHDRAWN' AND m.role <> 'ADMIN'")
    List<NotificationForMemberResponseDto> findActiveMembersWithPoliceUnit();

}
