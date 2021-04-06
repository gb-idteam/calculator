package ru.systemairac.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.service.allinterface.FileService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.security.Principal;

@RequestMapping("/file")
@Controller
public class FileController {

    private final ServletContext servletContext;
    private final FileService fileService;

    public FileController(ServletContext servletContext, FileService fileService) {
        this.servletContext = servletContext;
        this.fileService = fileService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{name}")
    public void getFile(@PathVariable String name, HttpServletResponse response, Principal principal) throws IOException {
        response.setHeader("content-type", "application/pdf"); // да, пока не рассмотрены другие типы файлов

        if (!fileService.fileExists(name)) {
            response.sendError(404);
            return;
        }

        if (!fileService.fileBelongsToUser(name, principal.getName())) {
            response.sendError(401);
            return;
        }

        try (FileInputStream fis = new FileInputStream(fileService.getPath(name).toFile());
             OutputStream os = response.getOutputStream()) {
            fis.transferTo(os);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("Exception when sending file " + name + ": " + e.getMessage());
        }
    }


}
