package com.test.wex.transaction.service;

import com.test.wex.transaction.dto.PurchaseTransactionDto;
import com.test.wex.transaction.exception.PurchaseCannotBeConvertedException;
import com.test.wex.transaction.integration.api.TreasuryRatesExchangeApi;
import com.test.wex.transaction.integration.api.domain.ExchangeRateData;
import com.test.wex.transaction.mapper.PurchaseTransactionMapper;
import com.test.wex.transaction.model.PurchaseTransaction;
import com.test.wex.transaction.repository.PurchaseTransactionRepository;
import com.test.wex.transaction.util.DateUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseTransactionService {

    private static final String API_FILTER = "currency:eq:%s,effective_date:lte:%s,effective_date:gte:%s";

    PurchaseTransactionMapper mapper;
    PurchaseTransactionRepository repository;
    DateUtil dateUtil;
    TreasuryRatesExchangeApi treasuryRatesExchangeApi;

    @Transactional
    public PurchaseTransactionDto createPurchaseTransaction(@Valid PurchaseTransactionDto dto) {
        var purchaseTransaction = mapper.createModelFromDto(dto);
        return mapper.createDtoFromModel(repository.save(purchaseTransaction));
    }

    public List<PurchaseTransactionDto> listConvertedPurchaseTransactions(String currency) {
        return repository.findAll().stream()
                .map(transaction -> mapDtoWithConvertedValue(transaction, currency))
                .toList();
    }

    private PurchaseTransactionDto mapDtoWithConvertedValue(PurchaseTransaction transaction, String currency) {
        var exchangeRateData = fetchExchangeRate(transaction, currency);
        var exchangeRate = exchangeRateData.getExchangeRate();
        return mapper.createDtoFromModelWithConvertedValue(
                transaction,
                exchangeRate,
                transaction.getUsdPurchaseAmount().multiply(exchangeRate));
    }

    private ExchangeRateData fetchExchangeRate(PurchaseTransaction transaction, String currency) {
        var transactionDate = transaction.getTransactionDate();
        var dateFrom = dateUtil.localDateToString(transactionDate);
        var dateTo = dateUtil.localDateToString(transactionDate.minusMonths(6));

        var filter = format(API_FILTER, currency, dateFrom, dateTo);
        return treasuryRatesExchangeApi.getRatesExchangeData(filter).getData().stream()
                .findFirst()
                .orElseThrow(() -> new PurchaseCannotBeConvertedException(transaction));
    }
}
