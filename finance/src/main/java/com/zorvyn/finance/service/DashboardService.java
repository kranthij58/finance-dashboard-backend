package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.mapper.FinancialRecordMapper;
import com.zorvyn.finance.dto.response.DashboardResponse;
import com.zorvyn.finance.dto.response.FinancialRecordResponse;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final FinancialRecordRepository recordRepo;

    public DashboardService(FinancialRecordRepository repo) {
        this.recordRepo = repo;
    }

    private DashboardResponse getTrends(LocalDate startDate) {
        List<FinancialRecordResponse> records = recordRepo.findByDateAfter(startDate)
                .stream()
                .map(FinancialRecordMapper::toResponse)
                .toList();

        BigDecimal totalIncome = records.stream()
                .filter(r -> r.getType().equals("INCOME"))
                .map(FinancialRecordResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = records.stream()
                .filter(r -> r.getType().equals("EXPENSE"))
                .map(FinancialRecordResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .recentActivity(records)
                .build();
    }

    public DashboardResponse getDashboardDataWeekly() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return getTrends(oneWeekAgo);

    }

    public DashboardResponse getDashboardDataMonthly() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return getTrends(oneMonthAgo);
    }

    public DashboardResponse getSummary() {
        BigDecimal totalIncome = recordRepo.sumByType("INCOME");
        BigDecimal totalExpenses = recordRepo.sumByType("EXPENSE");

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        List<Object[]> categoryData = recordRepo.sumByCategory();
        Map<String, BigDecimal> categoryWiseTotals = new HashMap<>();
        for (Object[] row : categoryData) {
            categoryWiseTotals.put((String) row[0], (BigDecimal) row[1]);
        }

        List<FinancialRecordResponse> recentActivity = recordRepo.findByDeletedAtIsNull()
                .stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(5)
                .map(FinancialRecordMapper::toResponse)
                .toList();

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .categoryWiseTotals(categoryWiseTotals)
                .recentActivity(recentActivity)
                .build();
    }
}
