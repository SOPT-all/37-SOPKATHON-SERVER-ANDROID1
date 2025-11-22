package sopt.server.android1.domain.balanceGame.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sopt.server.android1.domain.balanceGame.dto.request.CreateBalanceGameRequestDto;
import sopt.server.android1.domain.balanceGame.entity.ECategory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateBalanceGameCommandDto(
        String title,

        String option1Title,

        String option2Title,

        LocalDateTime deadline,

        ECategory category
) {
    public static CreateBalanceGameCommandDto from(CreateBalanceGameRequestDto request){

        LocalDateTime deadline = request.deadline().plusDays(7);

        return new CreateBalanceGameCommandDto(
                request.title(),
                request.option1Title(),
                request.option2Title(),
                deadline,
                request.category()
        );
    }
}
