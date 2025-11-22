package sopt.server.android1.domain.balanceGame.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sopt.server.android1.domain.balanceGame.entity.ECategory;

import java.time.LocalDateTime;

public record CreateBalanceGameRequestDto(
        @NotBlank
        @Schema(example = "피자냐 치킨이냐")
        String title,

        @NotBlank
        @Schema(example = "피자")
        String option1Title,

        @NotBlank
        @Schema(example = "치킨")
        String option2Title,

        @NotNull
        LocalDateTime deadline,

        @NotNull
        @Schema(example = "HUMOR")
        ECategory category
) {
}
