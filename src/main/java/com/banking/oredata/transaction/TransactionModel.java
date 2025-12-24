package com.banking.oredata.transaction;

import com.banking.oredata.base.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(
    name = "transaction_model",
    indexes = {
        @Index(name = "idx_tx_from_account", columnList = "fromAccountId"),
        @Index(name = "idx_tx_to_account", columnList = "toAccountId")
    }
)
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID fromAccountId;
    @Column(nullable = false)
    private UUID toAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
}
