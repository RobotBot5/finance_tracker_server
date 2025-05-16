package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.analytics.AnalyticsDailySummary;
import com.robotbot.finance_tracker_server.domain.dto.analytics.AnalyticsDailySummaryResponse;
import com.robotbot.finance_tracker_server.domain.dto.analytics.CategoriesAnalyticsResponse;
import com.robotbot.finance_tracker_server.domain.dto.analytics.CategoryAnalyticsResponse;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.domain.entities.TransactionEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.mappers.impls.AnalyticsMapper;
import com.robotbot.finance_tracker_server.mappers.impls.CategoryMapper;
import com.robotbot.finance_tracker_server.repositories.TransactionRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.AnalyticsService;
import com.robotbot.finance_tracker_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final AnalyticsMapper analyticsMapper;
    private final CategoryMapper categoryMapper;
    private final CurrencyRateUpdater currencyRateUpdater;

    @Override
    public AnalyticsDailySummaryResponse getDailySummary(
            UserPrincipal userPrincipal,
            LocalDate startDate,
            LocalDate endDate
    ) {
        UserEntity currentUser = userService.getUserByPrincipal(userPrincipal);

        List<TransactionEntity> transactions = transactionRepository.findByAccount_UserAndDateBetween(
                currentUser,
                startDate,
                endDate
        );

        Map<LocalDate, List<TransactionEntity>> groupedTransactions = transactions.stream()
                .collect(Collectors.groupingBy(TransactionEntity::getDate));

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

    @Override
    public CategoriesAnalyticsResponse getCategoriesAnalytics(UserPrincipal userPrincipal, Boolean isExpense, String sortOrder) {
        UserEntity currentUser = userService.getUserByPrincipal(userPrincipal);

        List<TransactionEntity> transactions = transactionRepository.findByAccount_UserAndCategory_IsExpense(currentUser, isExpense);

        Map<CategoryEntity, BigDecimal> aggregated = transactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCategory,
                        Collectors.mapping(
                                transaction -> currencyRateUpdater.convert(transaction.getAmount(),
                                        transaction.getAccount().getCurrency(),
                                        currentUser.getTargetCurrency()
                                ),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        BigDecimal totalAmount = aggregated.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean ascendingOrder = sortOrder.equalsIgnoreCase("asc");

        List<CategoryAnalyticsResponse> analyticsDtos = aggregated.entrySet().stream()
                .map(entry -> new CategoryAnalyticsResponse(
                        categoryMapper.mapEntityToResponse(entry.getKey()),
                        entry.getValue(),
                        entry.getValue()
                                .multiply(BigDecimal.valueOf(100))
                                .divide(totalAmount, 0, RoundingMode.HALF_UP)
                                .intValue()
                )).sorted((o1, o2) -> {
                    if (ascendingOrder) {
                        return o1.getTotalAmount().compareTo(o2.getTotalAmount());
                    } else {
                        return o2.getTotalAmount().compareTo(o1.getTotalAmount());
                    }
                })
                .collect(Collectors.toList());

        return analyticsMapper.mapCategoryAnalyticsListToResponse(analyticsDtos, totalAmount, currentUser.getTargetCurrency());
    }
}
