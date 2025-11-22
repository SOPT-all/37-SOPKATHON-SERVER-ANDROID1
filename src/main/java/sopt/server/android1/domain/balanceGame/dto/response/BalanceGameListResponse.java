package sopt.server.android1.domain.balanceGame.dto.response;

import java.util.List;

public record BalanceGameListResponse(
        List<BalanceGameDetailResponse> balanceGames
) {
}
