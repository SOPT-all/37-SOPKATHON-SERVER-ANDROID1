package sopt.server.android1.domain.comment.dto.response;

import sopt.server.android1.domain.comment.entity.Comment;
import sopt.server.android1.domain.comment.entity.EGameOption;

import java.time.LocalDateTime;
import java.util.List;

public record GetCommentResponseDto(
        EGameOption option,
        String content,
        String author,
        LocalDateTime createdAt
) {

    public static List<GetCommentResponseDto> of(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new GetCommentResponseDto(
                        comment.getGameOption(),
                        comment.getContent(),
                        comment.getMember().getNickname(), // 또는 comment.getAuthor()
                        comment.getCreatedAt()
                ))
                .toList();
    }
}
