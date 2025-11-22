package sopt.server.android1.domain.participant.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.server.android1.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum GameParticipantErrorCode implements ErrorCode {

    NOT_FOUND_GAME_PARTICIPANT(HttpStatus.BAD_REQUEST, "GAME_PARTICIPANT_001", "투표를 하지 않은 게임에는 댓글을 달 수 없습니다.");

    private final HttpStatus status;
    private final String prefix;
    private final String message;
}
