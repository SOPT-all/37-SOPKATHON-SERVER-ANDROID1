package sopt.server.android1.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.balanceGame.exception.BalanceGameErrorCode;
import sopt.server.android1.domain.balanceGame.repository.BalanceGameRepository;
import sopt.server.android1.domain.comment.dto.command.CreateCommentCommandDto;
import sopt.server.android1.domain.comment.dto.response.GetCommentResponseDto;
import sopt.server.android1.domain.comment.dto.response.ListGetCommentResponseDto;
import sopt.server.android1.domain.comment.entity.Comment;
import sopt.server.android1.domain.comment.repository.CommentRepository;
import sopt.server.android1.domain.member.entity.Member;
import sopt.server.android1.domain.member.exception.MemberErrorCode;
import sopt.server.android1.domain.member.repository.MemberRepository;
import sopt.server.android1.domain.participant.entity.GameParticipant;
import sopt.server.android1.domain.participant.exception.GameParticipantErrorCode;
import sopt.server.android1.domain.participant.repository.GameParticipantRepository;
import sopt.server.android1.global.exception.BaseException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BalanceGameRepository balanceGameRepository;
    private final MemberRepository memberRepository;
    private final GameParticipantRepository gameParticipantRepository;

    @Transactional
    public void createComment(CreateCommentCommandDto command) {
        BalanceGame balanceGame = balanceGameRepository.findById(command.balanceGameId())
                .orElseThrow(() -> BaseException.type(BalanceGameErrorCode.NOT_FOUND_BALANCE_GAME));

        Member member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER));

        GameParticipant gameParticipant = gameParticipantRepository
                .findByMemberIdAndBalanceGameId(command.memberId(), command.balanceGameId())
                .orElseThrow(() -> BaseException.type(GameParticipantErrorCode.NOT_FOUND_GAME_PARTICIPANT));

        Comment comment = Comment.create(
                gameParticipant.getGameOption(),
                command.content(),
                balanceGame,
                member);

        commentRepository.save(comment);

        balanceGame.addComment(comment);
    }

    @Transactional(readOnly = true)
    public ListGetCommentResponseDto getComment(Long balanceGameId){

        BalanceGame balanceGame = balanceGameRepository.findById(balanceGameId)
                .orElseThrow(() -> BaseException.type(BalanceGameErrorCode.NOT_FOUND_BALANCE_GAME));

        List<Comment> comments = balanceGame.getComments();

        return ListGetCommentResponseDto.of(GetCommentResponseDto.of(comments));
    }
}
