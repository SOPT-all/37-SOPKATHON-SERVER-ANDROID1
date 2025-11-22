package sopt.server.android1.domain.balanceGame.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.server.android1.domain.balanceGame.dto.command.CreateBalanceGameCommandDto;
import sopt.server.android1.domain.balanceGame.dto.request.CreateBalanceGameRequestDto;
import sopt.server.android1.domain.balanceGame.service.BalanceGameService;

@RestController
@RequestMapping("/api/v1/balance-games")
@RequiredArgsConstructor
public class BalanceGameController {

    private final BalanceGameService balanceGameService;

    @PostMapping
    public void createBalanceGame(
            @Valid @RequestBody CreateBalanceGameRequestDto request
    ) {
        balanceGameService.createBalanceGame(CreateBalanceGameCommandDto.from(request));
    }
}
