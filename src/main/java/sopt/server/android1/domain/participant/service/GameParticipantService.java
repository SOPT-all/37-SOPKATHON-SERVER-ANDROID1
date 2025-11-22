package sopt.server.android1.domain.participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.balanceGame.exception.BalanceGameErrorCode;
import sopt.server.android1.domain.balanceGame.repository.BalanceGameRepository;
import sopt.server.android1.domain.member.entity.Member;
import sopt.server.android1.domain.member.exception.MemberErrorCode;
import sopt.server.android1.domain.member.repository.MemberRepository;
import sopt.server.android1.domain.participant.dto.command.CreateGameParticipantCommandDto;
import sopt.server.android1.domain.participant.entity.GameParticipant;
import sopt.server.android1.domain.participant.repository.GameParticipantRepository;
import sopt.server.android1.global.exception.BaseException;

@Service
@RequiredArgsConstructor
public class GameParticipantService {

    private final GameParticipantRepository gameParticipantRepository;
    private final MemberRepository memberRepository;
    private final BalanceGameRepository balanceGameRepository;

    public void createGameParticipant(CreateGameParticipantCommandDto command) {

        Member member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER));

        BalanceGame balanceGame = balanceGameRepository.findById(command.balanceGameId())
                .orElseThrow(() -> BaseException.type(BalanceGameErrorCode.NOT_FOUND_BALANCE_GAME));

        GameParticipant gameParticipant = gameParticipantRepository
                .findByMemberIdAndBalanceGameId(command.memberId(), command.balanceGameId())
                .map(existing -> {
                    existing.updateOption(command.gameOption());
                    return existing;
                })
                .orElseGet(() -> GameParticipant.create(
                        command.gameOption(),
                        balanceGame,
                        member
                ));

        gameParticipantRepository.save(gameParticipant);
    }

}
