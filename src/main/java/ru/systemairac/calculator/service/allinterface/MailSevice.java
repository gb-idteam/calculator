package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.User;

import javax.mail.MessagingException;

public interface MailSevice {
    void sendMail(String email, String subject, String text) throws MessagingException;
    void sendCalculationMail(String code, User user);
}
