package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.service.allinterface.FileService;
import ru.systemairac.calculator.service.allinterface.MailService;

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
    @GetMapping("/show-and-send/{name}")
    public void showAndSendFile(@PathVariable String name, HttpSession session, HttpServletResponse response, Principal principal) throws IOException {
        response.setHeader("content-type", "application/pdf");
        if (checkFile(name, response, principal)) return;
        String projectTitle = getProjectTitle(session);
        sendFileToMail(name, principal,projectTitle);
        viewFile(name, response);
        fileService.deleteFile(fileService.getPath(name).toFile());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/show/{name}")
    public void showFile(@PathVariable String name, HttpSession session, HttpServletResponse response, Principal principal) throws IOException {
        response.setHeader("content-type", "application/pdf");
        if (checkFile(name, response, principal)) return;
        viewFile(name, response);
        fileService.deleteFile(fileService.getPath(name).toFile());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/send/{name}")
    public void sendFile(@PathVariable String name, HttpSession session, HttpServletResponse response, Principal principal) throws IOException {
        if (checkFile(name, response, principal)) return;
        String projectTitle = getProjectTitle(session);
        sendFileToMail(name, principal,projectTitle);
        fileService.deleteFile(fileService.getPath(name).toFile());
    }

    private String getProjectTitle(HttpSession session) {
        Long idProject = (Long) session.getAttribute("projectId");
        if (idProject==null)
            throw new NullPointerException(
                    String.format("Нулевое значение проекта: idProject=%d", idProject));
        return projectRepository.findById(idProject).orElseThrow().getTitle();
    }

    private boolean checkFile(String name, HttpServletResponse response, Principal principal) throws IOException {
        if (!fileService.fileExists(name)) {
            response.sendError(404);
            return true;
        }
        if (!fileService.fileBelongsToUser(name, principal.getName())) {
            response.sendError(401);
            return true;
        }
        return false;
    }

    private void viewFile(String name, HttpServletResponse response){
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
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendFileToMail(String name, Principal principal, String projectTitle) {
        Runnable mailTask = () -> mailService.sendEstimateMail(principal.getName(), name,projectTitle);
        Thread thread1 = new Thread(mailTask);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
