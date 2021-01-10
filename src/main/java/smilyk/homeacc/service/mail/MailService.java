package smilyk.homeacc.service.mail;

import smilyk.homeacc.dto.VerificationMailDto;

import javax.mail.MessagingException;

public interface MailService {
    void sendVerificationEmailMail(VerificationMailDto verificationMail) throws MessagingException;
}
