package sopt.server.android1.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.like.entity.GameLike;

import java.util.List;
import java.util.Optional;

public interface GameLikeRepository extends JpaRepository<GameLike, Long> {

    List<GameLike> findAllByMemberIdAndBalanceGameIdIn(Long memberId, List<Long> balanceGameIds);
    Optional<GameLike> findByBalanceGameIdAndMemberId(Long balanceGameId, Long memberId);
}
