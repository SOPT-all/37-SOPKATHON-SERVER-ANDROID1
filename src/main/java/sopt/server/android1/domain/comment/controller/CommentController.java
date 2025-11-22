package sopt.server.android1.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sopt.server.android1.domain.comment.dto.command.CreateCommentCommandDto;
import sopt.server.android1.domain.comment.dto.request.CreateCommentRequestDto;
import sopt.server.android1.domain.comment.dto.response.GetCommentResponseDto;
import sopt.server.android1.domain.comment.dto.response.ListGetCommentResponseDto;
import sopt.server.android1.domain.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balance-games/{balanceGameId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void createComment(
            @PathVariable Long balanceGameId,
            @RequestHeader(name = "X-Member-Id") Long memberId,
            @RequestBody @Valid CreateCommentRequestDto request) {
        commentService.createComment(CreateCommentCommandDto
                .of(memberId, balanceGameId, request));
    }

    @GetMapping
    public ListGetCommentResponseDto getComment(
            @PathVariable Long balanceGameId,
            @RequestHeader(name = "X-Member_Id") Long memberId
    ){
        return commentService.getComment(balanceGameId);
    }
}
