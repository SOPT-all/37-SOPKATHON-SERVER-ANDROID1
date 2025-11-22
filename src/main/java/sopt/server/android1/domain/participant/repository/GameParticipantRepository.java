package sopt.server.android1.domain.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.participant.entity.GameParticipant;

import java.util.Optional;

public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {
    Optional<GameParticipant> findByMemberIdAndBalanceGameId(Long memberId, Long balanceGameId);
}
