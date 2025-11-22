package sopt.server.android1.domain.like.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sopt.server.android1.domain.like.dto.command.UpdateLikeCommandDto;
import sopt.server.android1.domain.like.dto.request.UpdateLikeRequestDto;
import sopt.server.android1.domain.like.service.GameLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balance-games/{balanceGameId}")
public class GameLikeController {

    private final GameLikeService gameLikeService;

    @PostMapping("/like")
    public void updateLike(
            @RequestHeader("X-Member-Id") Long memberId,
            @PathVariable Long balanceGameId,
            @RequestBody @Valid UpdateLikeRequestDto request
            ){

        gameLikeService.updateLike(UpdateLikeCommandDto.of(memberId, balanceGameId, request));

    }
}
