package com.test.wex.transaction.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.wex.transaction.dto.PurchaseTransactionDto;
import com.test.wex.transaction.dto.Views;
import com.test.wex.transaction.service.PurchaseTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase_transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseTransactionController {

    PurchaseTransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Read.class)
    PurchaseTransactionDto createPurchaseTransaction(
            @JsonView(Views.Create.class) @RequestBody PurchaseTransactionDto dto) {
        return service.createPurchaseTransaction(dto);
    }

    @GetMapping("/list/{currency}")
    List<PurchaseTransactionDto> listConvertedPurchaseTransactions(
            @PathVariable String currency) {
        return service.listConvertedPurchaseTransactions(currency);
    }
}
