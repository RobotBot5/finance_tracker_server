package com.robotbot.finance_tracker_server.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryCreateRequest {

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Is expense cannot be null")
    private Boolean isExpense;

    @NotNull(message = "Icon id cannot be null")
    private Long iconId;

}
