package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.User;

public interface MailMessageBuilder {
    String buildConfirmationEmail(String code, User user);
}
