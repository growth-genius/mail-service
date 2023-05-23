package com.sgyj.mailservice.modules.client;


import com.sgyj.commonservice.dto.account.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="account-service")
public interface AccountServiceClient {

    @GetMapping("/account/{accountId}")
    AccountDto getAccount(@PathVariable String accountId);

}
