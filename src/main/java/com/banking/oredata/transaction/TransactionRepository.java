package com.banking.oredata.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    @Query("""
        SELECT t
        FROM TransactionModel t
        WHERE t.fromAccountId = :accountId
           OR t.toAccountId = :accountId
    """)
    List<TransactionModel> findByAccountId(@Param("accountId") UUID accountId);}
