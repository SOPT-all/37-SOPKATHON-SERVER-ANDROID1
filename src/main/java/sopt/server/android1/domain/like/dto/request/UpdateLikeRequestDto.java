package sopt.server.android1.domain.like.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateLikeRequestDto(
        @NotNull
        boolean isLike
) {
}
