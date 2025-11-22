package sopt.server.android1.domain.balanceGame.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopt.server.android1.domain.balanceGame.dto.response.BalanceGameDetailResponse;
import sopt.server.android1.domain.balanceGame.dto.response.BalanceGameListResponse;
import sopt.server.android1.domain.balanceGame.entity.ECategory;
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

    // 마감이 되지않은 밸런스 게임 리스트 조회(쿼리에 따라 카테고리 필터링 가능)
    @GetMapping
    public BalanceGameListResponse getBalanceGameList(@RequestHeader("X-Member-Id") Long memberId,
                                                      @RequestParam(required = false) ECategory category) {
        return balanceGameService.getBalanceGameList(memberId, category);
    }

    // 밸런스 게임 상세 조회
    @GetMapping("/{balanceGameId}")
    public BalanceGameDetailResponse getBalanceGameDetail(@RequestHeader("X-Member-Id") Long memberId,
                                                          @PathVariable Long balanceGameId) {
        return balanceGameService.getBalanceGameDetail(memberId, balanceGameId);
    }

    // 오늘의 밸런스 게임 조회
    @GetMapping("/today")
    public BalanceGameDetailResponse getTodayBalanceGame(@RequestHeader("X-Member-Id") Long memberId) {
        return balanceGameService.getTodayBalanceGame(memberId);
    }

    // 마감일이 지나지 않고 좋아요 수가 가장 많은 밸런스 게임 조회
    @GetMapping("/hot")
    public BalanceGameListResponse getHotBalanceGames(@RequestHeader("X-Member-Id") Long memberId) {
        return balanceGameService.getHotBalanceGames(memberId);
    }

    // 마감일이 지나지 않은 내가 참여하고 있는 밸런스 게임 조회
    @GetMapping("/current-participants")
    public BalanceGameListResponse getBalanceGamesWithCurrentParticipants(@RequestHeader("X-Member-Id") Long memberId) {
        return balanceGameService.getBalanceGamesWithCurrentParticipants(memberId);
    }

    // 마감일이 지난 내가 참여한 밸런스 게임 조회
    @GetMapping("/past-participants")
    public BalanceGameListResponse getBalanceGamesWithPastParticipants(@RequestHeader("X-Member-Id") Long memberId) {
        return balanceGameService.getBalanceGamesWithPastParticipants(memberId);
    }
}
