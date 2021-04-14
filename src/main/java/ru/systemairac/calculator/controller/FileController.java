package ru.systemairac.calculator.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.dto.EstimateDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.service.allinterface.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;

@RequestMapping("/file")
@Controller
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
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
        fileService.deleteFile(fileService.getPath(name).toFile());
    }

}
