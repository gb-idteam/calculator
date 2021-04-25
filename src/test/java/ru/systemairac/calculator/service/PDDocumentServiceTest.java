package ru.systemairac.calculator.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.service.allinterface.FileService;
import ru.systemairac.calculator.service.allinterface.PDDocumentService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.UserService;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class PDDocumentServiceTest {

    @Autowired
    private PDDocumentService service;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    private static FakeGenerator fakeGenerator;

    @BeforeAll
    static void init() {
        fakeGenerator = new FakeGenerator();
    }

    @Test
    void toPDDocument() throws IOException {
        ProjectDto projectDto = ProjectDto.builder()
                .title("Офисный комплекс \"Мечта\"")
                .address("Российская Федерация, г. Москва, " +
                        "Ленинский пр-т, стр. 123")
                .build();

        TechDataDto techDataDto = fakeGenerator.fakeTechDataDto();

        UserDto userDto = fakeGenerator.fakeUserDto();

        HumidifierDto humidifierDto = fakeGenerator.fakeHumidifierDto();

        VaporDistributorDto vaporDistributorDto = fakeGenerator.fakeVaporDistributorDto();

        EstimateDto estimateDto = fakeGenerator.fakeEstimateDto();

        try (PDDocument document = service.toPDDocument(userDto, projectDto, techDataDto, estimateDto )) {
//            document.save("123.pdf");
        }
    }
}
