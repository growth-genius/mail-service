package com.sgyj.mailservice.modules;

import com.sgyj.commonservice.annotation.RestBaseAnnotation;
import com.sgyj.commonservice.dto.account.AccountDto;
import com.sgyj.commonservice.utils.ApiUtil;
import com.sgyj.mailservice.modules.service.MailService;
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
