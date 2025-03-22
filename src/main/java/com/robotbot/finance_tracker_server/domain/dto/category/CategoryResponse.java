package com.robotbot.finance_tracker_server.domain.dto.category;

import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String name;

    private Boolean isExpense;

    private Boolean isSystem;

    private IconEntity icon;
}
