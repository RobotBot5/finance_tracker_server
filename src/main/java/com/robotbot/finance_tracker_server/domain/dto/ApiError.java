package com.robotbot.finance_tracker_server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String title;

    private int status;

    private String detail;

    private String instance;
}

