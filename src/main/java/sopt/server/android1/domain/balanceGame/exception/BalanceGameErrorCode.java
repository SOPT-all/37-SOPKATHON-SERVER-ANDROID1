package sopt.server.android1.domain.balanceGame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.server.android1.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum BalanceGameErrorCode implements ErrorCode {

    NOT_FOUND_BALANCE_GAME(HttpStatus.NOT_FOUND, "BALANCE_GAME_001", "밸런스 게임을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String prefix;
    private final String message;
}
