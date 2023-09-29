CREATE TABLE purchase_transaction(
    id                      UUID NOT NULL,
    description             VARCHAR2(50) NOT NULL,
    transaction_date        DATE NOT NULL,
    usd_purchase_amount     NUMERIC(20,2) NOT NULL,

    CONSTRAINT pk_purchase_transaction PRIMARY KEY (id)
);