package com.aivle.platform.repository;

import com.aivle.platform.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberNameAndPersonPhone(String memberName, String personPhone);

    // 지구대/파출소 사용 여부 확인
    boolean existsByPoliceUnit_PoliceUnitId(Long policeUnitId);

}
