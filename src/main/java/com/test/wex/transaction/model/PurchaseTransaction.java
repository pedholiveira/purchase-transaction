package com.test.wex.transaction.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "purchase_transaction")
public class PurchaseTransaction {

    @Id
    @UuidGenerator
    UUID id;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    LocalDate transactionDate;
    @Column(nullable = false, scale = 2)
    BigDecimal usdPurchaseAmount;
}
