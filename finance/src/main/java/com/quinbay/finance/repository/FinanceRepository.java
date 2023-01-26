package com.quinbay.finance.repository;

import com.quinbay.finance.model.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinanceRepository extends JpaRepository<Finance, Integer> {
    Optional<Finance> findByTxncode(String txn);
}
