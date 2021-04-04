package ru.systemairac.calculator.service.allinterface;

import org.apache.pdfbox.pdmodel.PDDocument;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.UserDto;

import java.io.IOException;

public interface PDDocumentService {

    PDDocument toPDDocument(UserDto userDto, ProjectDto projectDto, TechDataDto techDataDto) throws IOException;
}
