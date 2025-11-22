package sopt.server.android1.domain.participant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import sopt.server.android1.domain.comment.entity.EGameOption;

public record CreateGameParticipantRequestDto(
        @NotNull
        @Schema(example = "OPTION1")
        EGameOption gameOption
) {
}
