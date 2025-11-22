package sopt.server.android1.domain.participant.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.participant.entity.GameParticipant;

public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {
    Optional<GameParticipant> findByMemberIdAndBalanceGameId(Long memberId, Long balanceGameId);

    List<GameParticipant> findAllByMemberIdAndBalanceGameIdIn(Long memberId, List<Long> balanceGameIds);

    List<GameParticipant> findAllByMemberIdAndBalanceGameDeadlineAfterOrderByBalanceGameDeadlineAsc(Long memberId, LocalDateTime deadline);

    List<GameParticipant> findAllByMemberIdAndBalanceGameDeadlineBeforeOrderByBalanceGameDeadlineDesc(Long memberId, LocalDateTime deadline);
}
