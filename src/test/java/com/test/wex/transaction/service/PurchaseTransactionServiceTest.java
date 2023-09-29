package com.test.wex.transaction.service;

import com.test.wex.transaction.dto.PurchaseTransactionDto;
import com.test.wex.transaction.exception.PurchaseCannotBeConvertedException;
import com.test.wex.transaction.repository.PurchaseTransactionRepository;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest
public class PurchaseTransactionServiceTest {

    @Autowired
    private PurchaseTransactionService service;

    @Autowired
    private PurchaseTransactionRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testingCreationOfPurchaseTransactionWhenDescriptionIsNull() {
        var dto = PurchaseTransactionDto.builder()
                .description(null)
                .transactionDate(LocalDate.now())
                .usdPurchaseAmount(BigDecimal.valueOf(5.56))
                .build();

        Assertions.assertThatThrownBy(() -> service.createPurchaseTransaction(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Description cannot be blank");
    }

    @Test
    public void testingCreationOfPurchaseTransactionWhenTransactionDateIsNull() {
        var dto = PurchaseTransactionDto.builder()
                .description("Purchase transaction")
                .transactionDate(null)
                .usdPurchaseAmount(BigDecimal.valueOf(5.56))
                .build();

        Assertions.assertThatThrownBy(() -> service.createPurchaseTransaction(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Transaction date cannot be null");
    }

    @Test
    public void testingCreationOfPurchaseTransactionWhenAmountIsNull() {
        var dto = PurchaseTransactionDto.builder()
                .description("Purchase transaction")
                .transactionDate(LocalDate.now())
                .usdPurchaseAmount(null)
                .build();

        Assertions.assertThatThrownBy(() -> service.createPurchaseTransaction(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Purchase amount cannot be null");
    }

    @Test
    public void testingCreationOfPurchaseTransactionWhenAmountIsNotPositive() {
        var dto = PurchaseTransactionDto.builder()
                .description("Purchase transaction")
                .transactionDate(LocalDate.now())
                .usdPurchaseAmount(BigDecimal.ZERO)
                .build();

        Assertions.assertThatThrownBy(() -> service.createPurchaseTransaction(dto))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Purchase amount must be a positive value");
    }

    @Test
    public void testingCreationOfPurchaseTransactionWithSuccess() {
        var dto = PurchaseTransactionDto.builder()
                .description("Purchase transaction")
                .transactionDate(LocalDate.now())
                .usdPurchaseAmount(BigDecimal.valueOf(4.32))
                .build();

        var result = service.createPurchaseTransaction(dto);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        Assertions.assertThat(result.getTransactionDate()).isEqualTo(dto.getTransactionDate());
        Assertions.assertThat(result.getUsdPurchaseAmount()).isEqualTo(dto.getUsdPurchaseAmount());
    }

    @Test
    public void testingGetPurchaseTransactionListConvertedWhenNoPurchaseIsAdded() {
        var currency = "Real";
        var result = service.listConvertedPurchaseTransactions(currency);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testingGetPurchaseTransactionListConvertedWhenNoExchangeRateIsFound() {
        addPurchaseTransaction(LocalDate.now().plusMonths(7));
        var currency = "Real";
        Assertions.assertThatThrownBy(() -> service.listConvertedPurchaseTransactions(currency))
                .isInstanceOf(PurchaseCannotBeConvertedException.class);
    }

    @Test
    public void testingGetPurchaseTransactionListConvertedWithSuccess() {
        addPurchaseTransaction(LocalDate.now());
        var currency = "Real";
        var transactions = service.listConvertedPurchaseTransactions(currency);
        Assertions.assertThat(transactions).isNotEmpty();
        Assertions.assertThat(transactions).map(PurchaseTransactionDto::getId).allMatch(Objects::nonNull);
        Assertions.assertThat(transactions).map(PurchaseTransactionDto::getDescription).allMatch(Objects::nonNull);
        Assertions.assertThat(transactions).map(PurchaseTransactionDto::getTransactionDate).allMatch(Objects::nonNull);
        Assertions.assertThat(transactions).map(PurchaseTransactionDto::getUsdPurchaseAmount).allMatch(Objects::nonNull);
        Assertions.assertThat(transactions).map(PurchaseTransactionDto::getExchangeRate).allMatch(Objects::nonNull);
        Assertions.assertThat(transactions).map(PurchaseTransactionDto::getConvertedPurchaseAmount).allMatch(Objects::nonNull);
    }

    private void addPurchaseTransaction(LocalDate transactionDate) {
        var dto = PurchaseTransactionDto.builder()
                .description("Purchase transaction")
                .transactionDate(transactionDate)
                .usdPurchaseAmount(BigDecimal.valueOf(4.32))
                .build();

        service.createPurchaseTransaction(dto);
    }
}
