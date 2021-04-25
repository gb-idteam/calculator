package ru.systemairac.calculator.service.allimplement;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.FileEntity;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.repository.FileRepository;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.service.allinterface.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final ProjectRepository projectRepository;
    private final FileRepository fileRepository;

    public FileServiceImpl(ProjectRepository projectRepository, FileRepository fileRepository) {
        this.projectRepository = projectRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Path getPath(String name) {
        return Path.of(name); // TODO: папка сохранения
    }

    @Override
    public String savePDDocument(PDDocument document, Long projectId) throws IOException {
        Project project = projectRepository.findById(projectId).orElseThrow( () ->
                new NoSuchElementException("Project with id=" + projectId + " not found in repository")
        );

        File file;
        String name;
        do {
            name = UUID.randomUUID().toString() + ".pdf";
            file = new File(name);
        } while (file.exists());
        try {
            document.save(file);
            document.close();
        } catch (IOException e) {
            throw new IOException("Document " + name + " couldn't be saved: " + e.getMessage());
        }
        fileRepository.save(new FileEntity(null, name, project));
        return name;
    }

    @Override
    public boolean fileExists(String fileName) {
        return Files.exists(getPath(fileName)); // позже заменить на fileRepository.findByName(fileName).isPresent()
    }

    @Override
    public boolean fileBelongsToUser(String fileName, String username) {
        return true; // пока заглушка
//
//        if (!fileExists(fileName)) {
//            throw new RuntimeException("Попытались проверить владельца для несуществующего файла \'" + fileName + "\'");
//        }
//        FileEntity fileEntity = fileRepository.findByName(fileName).orElseThrow();
//        Project project = fileEntity.getProject();
//        return project.getUser().getEmail().equals(username);
    }

    @Override
    public void deleteFile(File file) {
        if (fileExists(file.getName())) file.delete();
    }
}
