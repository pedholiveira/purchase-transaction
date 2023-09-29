package com.test.wex.transaction.exception;

import com.test.wex.transaction.model.PurchaseTransaction;

import static java.lang.String.format;

public class PurchaseCannotBeConvertedException extends RuntimeException {

    public PurchaseCannotBeConvertedException(PurchaseTransaction purchaseTransaction) {
        super(format("The purchase %s cannot be converted to the target currency.", purchaseTransaction.getDescription()));
    }
}
