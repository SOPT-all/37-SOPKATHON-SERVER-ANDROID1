package sopt.server.android1.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequestDto(
        @NotBlank
        @Schema(example = "와 이거 박빙이다.")
        String content
) {
}
