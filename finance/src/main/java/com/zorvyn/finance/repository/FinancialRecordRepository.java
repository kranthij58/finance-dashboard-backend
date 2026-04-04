package com.zorvyn.finance.repository;

import com.zorvyn.finance.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Long> {
    List<FinancialRecord> findByDeletedAtIsNull();
    List<FinancialRecord> findByType(String type);
    List<FinancialRecord> findByCategory(String category);
    List<FinancialRecord> findByDate(LocalDate date);

}
