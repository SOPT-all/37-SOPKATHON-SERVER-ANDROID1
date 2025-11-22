package sopt.server.android1.domain.balanceGame.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopt.server.android1.domain.comment.entity.Comment;
import sopt.server.android1.global.base.BaseTimeEntity;

@Getter
@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "balance_games")
public class BalanceGame extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String option1Title;

    @Column(nullable = false)
    private String option2Title;

    @Column(nullable = false)
    private long option1Total;

    @Column(nullable = false)
    private long option2Total;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ECategory category;

    public static BalanceGame create(final String title,
                                     final String option1Title,
                                     final String option2Title,
                                     final LocalDateTime deadline,
                                     final ECategory category) {
        return BalanceGame.builder()
                .title(title)
                .option1Title(option1Title)
                .option2Title(option2Title)
                .deadline(deadline)
                .category(category)
                .build();
    }

    @OneToMany(mappedBy = "balanceGame", orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    public void addComment(final Comment comment) {
        comments.add(comment);
    }
}
