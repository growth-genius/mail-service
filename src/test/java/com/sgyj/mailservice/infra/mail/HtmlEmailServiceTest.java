package com.sgyj.mailservice.infra.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sgyj.commonservice.dto.mail.EmailMessage;
import com.sgyj.commonservice.dto.mail.MailSubject;
import com.sgyj.mailservice.modules.entity.Email;
import com.sgyj.mailservice.modules.repository.MailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "dev")
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