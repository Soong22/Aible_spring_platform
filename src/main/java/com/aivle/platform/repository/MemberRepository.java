package com.aivle.platform.repository;

import com.aivle.platform.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberNameAndPersonPhone(String memberName, String personPhone);

}
