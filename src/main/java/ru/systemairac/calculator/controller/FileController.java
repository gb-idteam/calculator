package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.systemairac.calculator.service.allinterface.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/file")
@Controller
public class FileController {
    private final FileService fileService;
    private final MailService mailService;

    public FileController(FileService fileService, MailService mailService) {
        this.fileService = fileService;
        this.mailService = mailService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{name}")
    public void getAndSendFile(@PathVariable String name, HttpServletResponse response, Principal principal) throws IOException {
        response.setHeader("content-type", "application/pdf");

        if (!fileService.fileExists(name)) {
            response.sendError(404);
            return;
        }

        if (!fileService.fileBelongsToUser(name, principal.getName())) {
            response.sendError(401);
            return;
        }
        try {
            sendFileToMail(name, principal);
            viewFile(name, response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fileService.deleteFile(fileService.getPath(name).toFile());
    }

    private void viewFile(String name, HttpServletResponse response) throws InterruptedException {
        Runnable viewFileTask = () -> {
            try (FileInputStream fis = new FileInputStream(fileService.getPath(name).toFile());
                 OutputStream os = response.getOutputStream()) {
                fis.transferTo(os);
                response.flushBuffer();
            } catch (IOException e) {
                throw new RuntimeException("Exception when sending file " + name + ": " + e.getMessage());
            }
        };
        Thread thread2 = new Thread(viewFileTask);
        thread2.start();
        thread2.join();
    }

    private void sendFileToMail(String name, Principal principal) throws InterruptedException {
        Runnable mailTask = () -> {
            mailService.sendEstimateMail(principal.getName(), name);
        };
        Thread thread1 = new Thread(mailTask);
        thread1.start();
        thread1.join();
    }

}
