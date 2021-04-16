package ru.systemairac.calculator.service.allimplement;

import ru.systemairac.calculator.domain.User;

public interface MailSenderService {
    void sendActivateCode(User user);
}
