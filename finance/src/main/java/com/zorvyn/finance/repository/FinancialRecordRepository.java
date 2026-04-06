package com.zorvyn.finance.repository;

import com.zorvyn.finance.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Long> {
    List<FinancialRecord> findByDeletedAtIsNull();
    List<FinancialRecord> findByType(String type);
    List<FinancialRecord> findByCategory(String category);
    List<FinancialRecord> findByDate(LocalDate date);
    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type AND f.deletedAt IS NULL")
    BigDecimal sumByType(@Param("type") String type);

    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.deletedAt IS NULL GROUP BY f.category")
    List<Object[]> sumByCategory();

    @Query("SELECT f FROM FinancialRecord f WHERE f.date >= :startDate AND f.deletedAt IS NULL ORDER BY f.date DESC")
    List<FinancialRecord> findByDateAfter(@Param("startDate") LocalDate startDate);

}
