package sopt.server.android1.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.like.entity.GameLike;

import java.util.Optional;

public interface GameLikeRepository extends JpaRepository<GameLike, Long> {
    Optional<GameLike> findByBalanceGameIdAndMemberId(Long balanceGameId, Long memberId);
}
