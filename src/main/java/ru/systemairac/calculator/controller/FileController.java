package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.service.allinterface.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;

@RequestMapping("/file")
@Controller
public class FileController {
    private final FileService fileService;
    private final MailService mailService;
    private final ProjectRepository projectRepository;

    public FileController(FileService fileService, MailService mailService, ProjectRepository projectRepository) {
        this.fileService = fileService;
        this.mailService = mailService;
        this.projectRepository = projectRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{name}")
    public void getAndSendFile(@PathVariable String name, HttpSession session, HttpServletResponse response, Principal principal) throws IOException {
        response.setHeader("content-type", "application/pdf");
        Long idProject = (Long) session.getAttribute("projectId");
        if (idProject==null)
            throw new NullPointerException(
                    String.format("Нулевое значение проекта: idProject=%d", idProject));
        Project project = projectRepository.findById(idProject).orElseThrow();
        if (!fileService.fileExists(name)) {
            response.sendError(404);
            return;
        }
        if (!fileService.fileBelongsToUser(name, principal.getName())) {
            response.sendError(401);
            return;
        }
        try {
            sendFileToMail(name, principal,project.getTitle());
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

    private void sendFileToMail(String name, Principal principal, String projectTitle) throws InterruptedException {
        Runnable mailTask = () -> {
            mailService.sendEstimateMail(principal.getName(), name,projectTitle);
        };
        Thread thread1 = new Thread(mailTask);
        thread1.start();
        thread1.join();
    }

}
