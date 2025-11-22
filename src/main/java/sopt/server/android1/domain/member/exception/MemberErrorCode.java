package sopt.server.android1.domain.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.server.android1.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "MEMBER_001", "멤버를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String prefix;
    private final String message;
}