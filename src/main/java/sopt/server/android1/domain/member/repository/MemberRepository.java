package sopt.server.android1.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
