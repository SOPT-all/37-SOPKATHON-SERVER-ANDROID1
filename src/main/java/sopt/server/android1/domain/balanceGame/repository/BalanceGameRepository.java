package sopt.server.android1.domain.balanceGame.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;

public interface BalanceGameRepository extends JpaRepository<BalanceGame, Long>{
}
