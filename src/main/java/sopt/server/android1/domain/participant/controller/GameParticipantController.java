package sopt.server.android1.domain.participant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sopt.server.android1.domain.participant.dto.command.CreateGameParticipantCommandDto;
import sopt.server.android1.domain.participant.dto.request.CreateGameParticipantRequestDto;
import sopt.server.android1.domain.participant.service.GameParticipantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balance-games/{balanceId}")
public class GameParticipantController {

    private final GameParticipantService gameParticipantService;

    @PostMapping
    public void createGameParticipant(
            @PathVariable Long balanceId,
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody CreateGameParticipantRequestDto request
            ){
        gameParticipantService.createGameParticipant(CreateGameParticipantCommandDto.of(
                memberId,
                balanceId,
                request
        ));
    }
}
