package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.User;

public interface MailMessageBuilder {
    String buildCalculationEmail(String code, User user);
}
