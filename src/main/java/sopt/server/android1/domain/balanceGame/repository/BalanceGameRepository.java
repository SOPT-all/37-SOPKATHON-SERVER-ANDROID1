package sopt.server.android1.domain.balanceGame.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.balanceGame.entity.ECategory;

public interface BalanceGameRepository extends JpaRepository<BalanceGame, Long> {

    List<BalanceGame> findAllByDeadlineAfterOrderByDeadlineAsc(LocalDateTime deadline);

    List<BalanceGame> findAllByDeadlineAfterAndCategoryOrderByDeadlineAsc(LocalDateTime deadline, ECategory category);

    @Query("""
            select bg
            from BalanceGame bg
            left join GameLike gl on gl.balanceGame = bg
            where bg.deadline > :now
            group by bg
            order by count(gl) desc, bg.deadline asc
            """)
    List<BalanceGame> findHotBalanceGames(@Param("now") LocalDateTime now, Pageable pageable);
}
