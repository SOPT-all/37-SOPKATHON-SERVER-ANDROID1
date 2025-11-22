package sopt.server.android1.domain.participant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.server.android1.domain.balanceGame.entity.BalanceGame;
import sopt.server.android1.domain.comment.entity.EGameOption;
import sopt.server.android1.domain.member.entity.Member;
import sopt.server.android1.global.base.BaseTimeEntity;

@Getter
@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "game_participants", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_balance_game_member",
                columnNames = {"balance_game_id", "member_id"}
        )
})
public class GameParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EGameOption gameOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_game_id", nullable = false)
    private BalanceGame balanceGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static GameParticipant create(
            final EGameOption gameOption,
            final BalanceGame balanceGame,
            final Member member
    ){
        return GameParticipant.builder()
                .gameOption(gameOption)
                .balanceGame(balanceGame)
                .member(member).build();
    }

    public void updateOption(final EGameOption gameOption){
        this.gameOption = gameOption;
    }
}

