package ru.systemairac.calculator.service.allimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.service.allinterface.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender sender;
    private MailMessageBuilderImpl messageBuilder;

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilderImpl messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Override
    public void sendMail(String email, String subject, String text) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setText(text, true);
        helper.setSubject(subject);
        sender.send(message);
    }

    @Override
    public void sendCalculationMail(String code, User user) {
        try {
            sendMail (user.getEmail(),
                    String.format("Your confirmation code %s", code),

                    messageBuilder.buildCalculationEmail(code));
        } catch (MessagingException | MailException ex) {
        }
    }
}