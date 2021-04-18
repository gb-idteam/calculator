package ru.systemairac.calculator.service.allimplement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.service.allinterface.MailMessageBuilder;

@Service
public class MailMessageBuilderImpl implements MailMessageBuilder {
    private SpringTemplateEngine templateEngine;
    @Value("${local.server.port}")
    private int port;
    @Value("${local.server.host}")
    private String host;
    @Value("${server.servlet.context-path}")
    private String path;
    @Autowired
    public void setTemplateEngine(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildConfirmationEmail(String code, User user) {
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("user", user);
        context.setVariable("host", host);
        context.setVariable("port", port);
        context.setVariable("path", path);
//        templateEngine = new SpringTemplateEngine();
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCharacterEncoding("UTF-8");
//        templateResolver.setOrder(0);
//        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine.process("mail", context);
    }
}
