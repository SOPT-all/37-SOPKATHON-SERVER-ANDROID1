package sopt.server.android1.domain.balanceGame.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.server.android1.domain.balanceGame.dto.command.CreateBalanceGameCommandDto;
import sopt.server.android1.domain.balanceGame.dto.response.BalanceGameDetailResponse;
import sopt.server.android1.domain.balanceGame.dto.response.BalanceGameListResponse;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.balanceGame.entity.ECategory;
import sopt.server.android1.domain.balanceGame.repository.BalanceGameRepository;
import sopt.server.android1.domain.comment.entity.EGameOption;
import sopt.server.android1.domain.like.repository.GameLikeLikeCountProjection;
import sopt.server.android1.domain.like.repository.GameLikeRepository;
import sopt.server.android1.domain.participant.entity.GameParticipant;
import sopt.server.android1.domain.participant.repository.GameParticipantRepository;
import sopt.server.android1.global.exception.BaseException;
import sopt.server.android1.global.exception.CommonErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BalanceGameService {

    private final BalanceGameRepository balanceGameRepository;
    private final GameParticipantRepository gameParticipantRepository;
    private final GameLikeRepository gameLikeRepository;

    public void createBalanceGame(CreateBalanceGameCommandDto request) {
        BalanceGame balanceGame = BalanceGame.create(request.title(),
                request.option1Title(),
                request.option2Title(),
                request.deadline(),
                request.category());

        balanceGameRepository.save(balanceGame);
    }

    public BalanceGameListResponse getBalanceGameList(Long memberId, ECategory category) {
        LocalDateTime now = LocalDateTime.now();
        List<BalanceGame> balanceGames = category == null
                ? balanceGameRepository.findAllByDeadlineAfterOrderByDeadlineAsc(now)
                : balanceGameRepository.findAllByDeadlineAfterAndCategoryOrderByDeadlineAsc(now, category);

        return toListResponse(memberId, balanceGames);
    }

    public BalanceGameDetailResponse getBalanceGameDetail(Long memberId, Long balanceGameId) {
        BalanceGame balanceGame = balanceGameRepository.findById(balanceGameId)
                .orElseThrow(() -> BaseException.type(CommonErrorCode.NOT_FOUND_BALANCE_GAME));

        return toDetailResponse(memberId, balanceGame);
    }

    public BalanceGameDetailResponse getTodayBalanceGame(Long memberId) {
        List<BalanceGame> openGames = balanceGameRepository.findAllByDeadlineAfterOrderByDeadlineAsc(
                LocalDateTime.now());
        if (openGames.isEmpty()) {
            throw BaseException.type(CommonErrorCode.NOT_FOUND_BALANCE_GAME);
        }

        int index = ThreadLocalRandom.current().nextInt(openGames.size());
        BalanceGame randomGame = openGames.get(index);
        return toDetailResponse(memberId, randomGame);
    }

    public BalanceGameListResponse getHotBalanceGames(Long memberId) {
        List<BalanceGame> hotGames = balanceGameRepository
                .findHotBalanceGames(LocalDateTime.now(), PageRequest.of(0, 3));
        return toListResponse(memberId, hotGames);
    }

    public BalanceGameListResponse getBalanceGamesWithCurrentParticipants(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        List<BalanceGame> participating = gameParticipantRepository
                .findAllByMemberIdAndBalanceGameDeadlineAfterOrderByBalanceGameDeadlineAsc(memberId, now)
                .stream()
                .map(GameParticipant::getBalanceGame)
                .toList();

        return toListResponse(memberId, participating);
    }

    public BalanceGameListResponse getBalanceGamesWithPastParticipants(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        List<BalanceGame> pastGames = gameParticipantRepository
                .findAllByMemberIdAndBalanceGameDeadlineBeforeOrderByBalanceGameDeadlineDesc(memberId, now)
                .stream()
                .map(GameParticipant::getBalanceGame)
                .toList();

        return toListResponse(memberId, pastGames);
    }

    private BalanceGameListResponse toListResponse(Long memberId, List<BalanceGame> balanceGames) {
        if (balanceGames.isEmpty()) {
            return new BalanceGameListResponse(Collections.emptyList());
        }

        List<Long> balanceGameIds = balanceGames.stream()
                .map(BalanceGame::getId)
                .toList();

        Map<Long, EGameOption> memberSelections = getMemberSelections(memberId, balanceGameIds);
        Set<Long> likedGameIds = getLikedGameIds(memberId, balanceGameIds);
        Map<Long, Long> likeCounts = getLikeCounts(balanceGameIds);

        List<BalanceGameDetailResponse> responses = balanceGames.stream()
                .map(game -> buildResponse(game, memberSelections, likedGameIds, likeCounts))
                .toList();

        return new BalanceGameListResponse(responses);
    }

    private BalanceGameDetailResponse toDetailResponse(Long memberId, BalanceGame balanceGame) {
        List<Long> gameIdList = List.of(balanceGame.getId());
        Map<Long, EGameOption> memberSelections = getMemberSelections(memberId, gameIdList);
        Set<Long> likedGameIds = getLikedGameIds(memberId, gameIdList);
        Map<Long, Long> likeCounts = getLikeCounts(gameIdList);

        return buildResponse(balanceGame, memberSelections, likedGameIds, likeCounts);
    }

    private BalanceGameDetailResponse buildResponse(BalanceGame game,
                                                    Map<Long, EGameOption> memberSelections,
                                                    Set<Long> likedGameIds,
                                                    Map<Long, Long> likeCounts) {
        Long gameId = game.getId();
        return new BalanceGameDetailResponse(
                gameId,
                game.getTitle(),
                game.getOption1Title(),
                game.getOption2Title(),
                likedGameIds.contains(gameId),
                Math.toIntExact(likeCounts.getOrDefault(gameId, 0L)),
                memberSelections.getOrDefault(gameId, EGameOption.EMPTY),
                game.getOption1Total(),
                game.getOption2Total(),
                game.getDeadline(),
                game.getCategory()
        );
    }

    private Map<Long, EGameOption> getMemberSelections(Long memberId, List<Long> balanceGameIds) {
        if (balanceGameIds.isEmpty()) {
            return Collections.emptyMap();
        }

        return gameParticipantRepository
                .findAllByMemberIdAndBalanceGameIdIn(memberId, balanceGameIds)
                .stream()
                .collect(Collectors.toMap(
                        participation -> participation.getBalanceGame().getId(),
                        GameParticipant::getGameOption
                ));
    }

    private Set<Long> getLikedGameIds(Long memberId, List<Long> balanceGameIds) {
        if (balanceGameIds.isEmpty()) {
            return Collections.emptySet();
        }

        return gameLikeRepository.findAllByMemberIdAndBalanceGameIdIn(memberId, balanceGameIds)
                .stream()
                .map(gameLike -> gameLike.getBalanceGame().getId())
                .collect(Collectors.toSet());
    }

    private Map<Long, Long> getLikeCounts(List<Long> balanceGameIds) {
        if (balanceGameIds.isEmpty()) {
            return Collections.emptyMap();
        }

        return gameLikeRepository.countLikesByBalanceGameIds(balanceGameIds)
                .stream()
                .collect(Collectors.toMap(
                        GameLikeLikeCountProjection::getBalanceGameId,
                        GameLikeLikeCountProjection::getLikeCount
                ));
    }
}
