package sopt.server.android1.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.balanceGame.exception.BalanceGameErrorCode;
import sopt.server.android1.domain.balanceGame.repository.BalanceGameRepository;
import sopt.server.android1.domain.like.dto.command.UpdateLikeCommandDto;
import sopt.server.android1.domain.like.entity.GameLike;
import sopt.server.android1.domain.like.exception.LikeErrorCode;
import sopt.server.android1.domain.like.repository.GameLikeRepository;
import sopt.server.android1.domain.member.entity.Member;
import sopt.server.android1.domain.member.exception.MemberErrorCode;
import sopt.server.android1.domain.member.repository.MemberRepository;
import sopt.server.android1.global.exception.BaseException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameLikeService {
    private final GameLikeRepository gameLikeRepository;
    private final BalanceGameRepository balanceGameRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void updateLike(UpdateLikeCommandDto command) {
        BalanceGame balanceGame = balanceGameRepository.findById(command.balanceGameId())
                .orElseThrow(() -> BaseException.type(BalanceGameErrorCode.NOT_FOUND_BALANCE_GAME));

        Member member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER));

        Optional<GameLike> existingLike = gameLikeRepository
                .findByBalanceGameIdAndMemberId(command.balanceGameId(), command.memberId());

        if (command.isLiked()) {
            // true인데 이미 좋아요 있으면 에러
            if (existingLike.isPresent()) {
                throw BaseException.type(LikeErrorCode.ALREADY_LIKED);
            }
            // 없으면 생성
            GameLike gameLike = GameLike.create(balanceGame, member);
            gameLikeRepository.save(gameLike);

        } else {
            // false인데 좋아요 없으면 에러
            if (existingLike.isEmpty()) {
                throw BaseException.type(LikeErrorCode.LIKE_NOT_FOUND);
            }
            // 있으면 삭제
            gameLikeRepository.delete(existingLike.get());
        }
    }
}
