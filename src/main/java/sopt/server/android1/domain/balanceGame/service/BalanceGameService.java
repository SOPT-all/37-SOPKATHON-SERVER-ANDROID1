package sopt.server.android1.domain.balanceGame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.server.android1.domain.balanceGame.dto.command.CreateBalanceGameCommandDto;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.balanceGame.repository.BalanceGameRepository;

@Service
@RequiredArgsConstructor
public class BalanceGameService {

    private final BalanceGameRepository balanceGameRepository;

    public void createBalanceGame(CreateBalanceGameCommandDto request) {
        BalanceGame balanceGame = BalanceGame.create(request.title(),
                request.option1Title(),
                request.option2Title(),
                request.deadline(),
                request.category());

        balanceGameRepository.save(balanceGame);
    }
}
