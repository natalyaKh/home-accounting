package smilyk.homeacc.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.Constants;
import smilyk.homeacc.constants.UserConstants;
import smilyk.homeacc.dto.ErrorEmailDto;
import smilyk.homeacc.dto.VerificationMailDto;
import smilyk.homeacc.exceptions.HomeaccException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class MailSenderImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);
    private static final String CURRENTLY_DATE = LocalDateTime.now().toLocalDate().toString();
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    Environment env;

    @Override
    public void sendVerificationEmailMail(VerificationMailDto verificationMail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        String email = verificationMail.getEmail();
        String tokenValue = verificationMail.getTokenValue();
        String lastName = verificationMail.getUserLastName();
        String firstName = verificationMail.getUserName();

        String VERIFY_LINK = env.getProperty("verification.link");

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
//        The HTML body for the email.
        final String htmlMsg =
                "<h1> Hi " + firstName + " " + lastName + ". Please verify your email address</h1>"
                        + "<p>Thank you for registering with our app. To complete registration process and be able to log in,"
                        + " click on the following link: "
                        + " <a href='" + VERIFY_LINK + "?token=" + tokenValue + "'>" + "<br/><br/>" + " Final step to complete your registration" +
                        "</a><br/><br/>"
                        + "Thank you! And we are waiting for you inside!";

        message.setContent(htmlMsg, "text/html");
        helper.setTo(email);
        helper.setSubject("Verification email from" + Constants.APPLICATION_NAME);
        try {
            this.javaMailSender.send(message);
            LOGGER.info(UserConstants.SEND_VERIFICATION_EMAIL + email);
        } catch (Exception ex) {
            LOGGER.error(UserConstants.SOMETHING_IS_WRONG_WITH + email);
            ErrorEmailDto errorEmailDto = ErrorEmailDto.builder()
                    .userEmail(email)
                    .userName(firstName + " " + lastName)
                    .build();
            sendErrorEmail(errorEmailDto);
        }
    }

    private void sendErrorEmail(ErrorEmailDto errorEmailDto) {
        String name = errorEmailDto.getUserName();
        String email = errorEmailDto.getUserEmail();
        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = true;
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, multipart, "utf-8");
            final String htmlMsg =
                    "Hi, admin ! "
                            + "something was wrong during sending verification email to " +
                            name + " with e-mail:  " + email + " in " + CURRENTLY_DATE + "<p> Please, check it";
            message.setContent(htmlMsg, "text/html");
            helper.setTo(env.getProperty("admin.email.address"));
            helper.setSubject("Error email from " + Constants.APPLICATION_NAME);
            this.javaMailSender.send(message);
            LOGGER.info(UserConstants.ERROR_EMAIL);
        } catch (MessagingException e) {
            LOGGER.info(UserConstants.ERROR_EMAIL_PROBLEMS);
            throw new HomeaccException(UserConstants.ERROR_EMAIL_PROBLEMS + e.getMessage());
        }
    }
}



