package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.User;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {
    void sendMessageWithAttachment(String mail, String subject, String text, String pathToAttachment) throws MessagingException, IOException;
    void sendMail(String email, String subject, String text) throws MessagingException;

    void sendEstimateMail(String toMail, String nameFile, String projectTitle);

    void sendConfirmationMail(String code, User user);
}
