package ru.systemairac.calculator.service.allinterface;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    Path getPath(String name);
    String savePDDocument(PDDocument document, Long projectId) throws IOException;
    boolean fileExists(String fileName);
    boolean fileBelongsToUser(String fileName, String username);
    void deleteFile(File file);
}
