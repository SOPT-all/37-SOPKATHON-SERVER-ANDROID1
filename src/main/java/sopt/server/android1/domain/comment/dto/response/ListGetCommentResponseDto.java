package sopt.server.android1.domain.comment.dto.response;

import java.util.List;

public record ListGetCommentResponseDto(
        List<GetCommentResponseDto> getCommentResponseDto
) {
    public static ListGetCommentResponseDto of(List<GetCommentResponseDto> list) {
        return new ListGetCommentResponseDto(list);
    }
}
