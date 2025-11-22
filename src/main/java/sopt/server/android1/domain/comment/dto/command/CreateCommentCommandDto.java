package sopt.server.android1.domain.comment.dto.command;

import sopt.server.android1.domain.comment.dto.request.CreateCommentRequestDto;

public record CreateCommentCommandDto(
        Long memberId,

        Long balanceGameId,

        String content
) {
    public static CreateCommentCommandDto of(Long memberId,
                                             Long balanceGameId,
                                             CreateCommentRequestDto request) {
        return new CreateCommentCommandDto(memberId, balanceGameId, request.content());
    }
}
