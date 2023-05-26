package com.gg.mailservice.modules;

import com.gg.commonservice.annotation.RestBaseAnnotation;
import com.gg.commonservice.dto.account.AccountDto;
import com.gg.commonservice.utils.ApiUtil;
import com.gg.mailservice.modules.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestBaseAnnotation
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/accounts/id/{accountId}")
    public ApiUtil.ApiResult<AccountDto> findAccount(@PathVariable String accountId) {
        return ApiUtil.success(mailService.getAccount(accountId));
    }

}
