package ru.systemairac.calculator.service.allimplement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.MailMessageBuilder;
import ru.systemairac.calculator.service.allinterface.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender sender;
    private final UserRepository userRepository;
    private MailMessageBuilder messageBuilder;
    @Value("${spring.mail.username}")
    private String username;
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    public MailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilderImpl messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Override
    public void sendMessageWithAttachment(String toMail, String subject, String text, String pathToAttachment) throws MessagingException, IOException {
            final MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            prepareMessage(toMail, subject, text, helper);
            FileSystemResource file = new FileSystemResource(pathToAttachment);
            byte[] templateContent = FileCopyUtils.copyToByteArray(file.getFile());
            helper.addAttachment(file.getFilename(),new ByteArrayResource(templateContent),"application/x-pdf");
            sender.send(message);
    }

    private void prepareMessage(String toMail, String subject, String text, MimeMessageHelper helper) throws MessagingException {
        helper.setSubject(subject);
        helper.setFrom(username);
        helper.setTo(toMail);
        helper.setText(text, true);
    }

    @Override
    public void sendMail(String toMail, String subject, String text) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        prepareMessage(toMail, subject, text, helper);
        sender.send(message);
    }
    @Override
    public void sendEstimateMail(String toMail, String nameFile,String projectTitle) {
        logger.info("--in the function of sendMail");
        User user = userRepository.findByEmail(toMail);
        if (!user.isConfirmed()) return; //TODO Вывод сообщения
        try {
            sendMessageWithAttachment (toMail,
                    String.format("Расчет увлажнителя проект %s - %s",projectTitle,new SimpleDateFormat().format(new Date())),
                    "Во вложении Вы найдете Ваш расчет",
                    nameFile);
            logger.info("--Mail Sent Successfully");
        } catch (MessagingException e) {
            logger.info("--Mail Sent failed ---> " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendConfirmationMail(String code, User user) {
        try {
            sendMail (user.getEmail(),
                    String.format("Your confirmation code %s", code),
                    messageBuilder.buildConfirmationEmail(code,user));
        } catch (MessagingException | MailException ex) {
        }
    }
}