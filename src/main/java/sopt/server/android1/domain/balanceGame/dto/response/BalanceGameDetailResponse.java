package sopt.server.android1.domain.balanceGame.dto.response;

import java.time.LocalDateTime;
import sopt.server.android1.domain.balanceGame.entity.ECategory;
import sopt.server.android1.domain.comment.entity.EGameOption;

public record BalanceGameDetailResponse(
    Long id,
    String title,
    String option1Title,
    String option2Title,
    boolean isLike,
    int likeCount,
    EGameOption memberOption,
    long option1Total,
    long option2Total,
    LocalDateTime deadline,
    ECategory category
) {
}
