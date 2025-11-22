package sopt.server.android1.domain.like.dto.command;

import sopt.server.android1.domain.like.dto.request.UpdateLikeRequestDto;

public record UpdateLikeCommandDto(
        Long memberId,

        Long balanceGameId,

        boolean isLiked
) {
    public static UpdateLikeCommandDto of(Long memberId, Long balanceGameId, UpdateLikeRequestDto request) {
        return new  UpdateLikeCommandDto(memberId, balanceGameId, request.isLike());
    }
}
