package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.analytics.AnalyticsDailySummary;
import com.robotbot.finance_tracker_server.domain.dto.analytics.AnalyticsDailySummaryResponse;
import com.robotbot.finance_tracker_server.domain.entities.TransactionEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.repositories.TransactionRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.AnalyticsService;
import com.robotbot.finance_tracker_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;

    @Override
    public AnalyticsDailySummaryResponse getDailySummary(
            UserPrincipal userPrincipal,
            LocalDate startDate,
            LocalDate endDate,
            ZoneId zoneId
    ) {
        OffsetDateTime startDateTime = startDate.atStartOfDay(zoneId).toOffsetDateTime();

        OffsetDateTime endDateTime = endDate.plusDays(1).atStartOfDay(zoneId).toOffsetDateTime().minusNanos(1);

        UserEntity currentUser = userService.getUserByPrincipal(userPrincipal);

        List<TransactionEntity> transactions = transactionRepository.findByAccount_UserAndTimeBetween(
                currentUser,
                startDateTime,
                endDateTime
        );

        Map<LocalDate, List<TransactionEntity>> groupedTransactions = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTime().toLocalDate()));

        List<AnalyticsDailySummary> dailySummaries = groupedTransactions.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<TransactionEntity> transactionsByDate = entry.getValue();

                    BigDecimal totalExpense = transactionsByDate.stream()
                            .filter(transaction -> transaction.getCategory().getIsExpense())
                            .map(TransactionEntity::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalIncome = transactionsByDate.stream()
                            .filter(transaction -> !transaction.getCategory().getIsExpense())
                            .map(TransactionEntity::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal net = totalIncome.subtract(totalExpense);

                    return AnalyticsDailySummary.builder()
                            .date(date)
                            .totalExpense(totalExpense)
                            .totalIncome(totalIncome)
                            .net(net)
                            .build();
                })
                .sorted(Comparator.comparing(AnalyticsDailySummary::getDate))
                .collect(Collectors.toList());

        return AnalyticsDailySummaryResponse.builder()
                .analytics(dailySummaries)
                .build();
    }
}
