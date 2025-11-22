package sopt.server.android1.domain.participant.dto.command;

import sopt.server.android1.domain.comment.entity.EGameOption;
import sopt.server.android1.domain.participant.dto.request.CreateGameParticipantRequestDto;

public record CreateGameParticipantCommandDto(

        Long memberId,

        Long balanceGameId,

        EGameOption gameOption
) {

    public static CreateGameParticipantCommandDto of(
            final Long memberId,
            final Long balanceGameId,
            final CreateGameParticipantRequestDto request
    ){
        return new CreateGameParticipantCommandDto(memberId, balanceGameId, request.gameOption());
    }
}
