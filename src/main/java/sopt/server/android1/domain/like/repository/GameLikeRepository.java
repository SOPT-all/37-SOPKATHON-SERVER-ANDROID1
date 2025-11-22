package sopt.server.android1.domain.like.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.like.entity.GameLike;

public interface GameLikeRepository extends JpaRepository<GameLike, Long> {

    List<GameLike> findAllByMemberIdAndBalanceGameIdIn(Long memberId, List<Long> balanceGameIds);
}
