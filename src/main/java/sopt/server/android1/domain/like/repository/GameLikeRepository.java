package sopt.server.android1.domain.like.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.server.android1.domain.like.entity.GameLike;

public interface GameLikeRepository extends JpaRepository<GameLike, Long> {

    List<GameLike> findAllByMemberIdAndBalanceGameIdIn(Long memberId, List<Long> balanceGameIds);
    Optional<GameLike> findByBalanceGameIdAndMemberId(Long balanceGameId, Long memberId);

    @Query("""
            select gl.balanceGame.id as balanceGameId, count(gl.id) as likeCount
            from GameLike gl
            where gl.balanceGame.id in :balanceGameIds
            group by gl.balanceGame.id
            """)
    List<GameLikeLikeCountProjection> countLikesByBalanceGameIds(@Param("balanceGameIds") List<Long> balanceGameIds);
}
