package com.test.wex.transaction.integration.api;

import com.test.wex.transaction.integration.api.domain.TreasuryRatesExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "treasury-rates-exchange-api", url = "${treasury-rates-exchange.api.url}")
public interface TreasuryRatesExchangeApi {

    @RequestMapping(method = RequestMethod.GET)
    TreasuryRatesExchangeResponse getRatesExchangeData(@RequestParam("filter") String filter);
}
