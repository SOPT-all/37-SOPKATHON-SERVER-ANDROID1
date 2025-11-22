package sopt.server.android1.domain.like.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.server.android1.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum LikeErrorCode implements ErrorCode {

    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "LIKE_001", "이미 좋아요 된 밸런스 게임입니다."),
    LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "LIKE_002", "없는 밸런스 게임은 false 할수 없습니다.");


    private final HttpStatus status;
    private final String prefix;
    private final String message;
}
