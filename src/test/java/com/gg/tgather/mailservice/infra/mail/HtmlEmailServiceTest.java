package com.gg.tgather.mailservice.infra.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import com.gg.tgather.commonservice.dto.mail.MailSubject;
import com.gg.tgather.mailservice.modules.entity.Email;
import com.gg.tgather.mailservice.modules.repository.MailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HtmlEmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MailRepository mailRepository;

    @Test
    @DisplayName("메일 전송 후 Email entity 저장 테스트")
    void sendEmail() throws Exception {
        String email = "leesg107@naver.com";
        EmailMessage emailMessage = EmailMessage.builder().accountId("ATUATOEUA").message("authCode").mailSubject(MailSubject.VALID_AUTHENTICATION_ACCOUNT)
            .to(email).build();
        emailService.sendEmail(emailMessage);

        Email emailEntity = mailRepository.findByEmailAddress(email).orElseThrow(Exception::new);

        assertEquals(email, emailEntity.getEmailAddress());
    }
}