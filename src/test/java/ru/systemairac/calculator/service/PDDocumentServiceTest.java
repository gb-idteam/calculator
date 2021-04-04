package ru.systemairac.calculator.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeCylinder;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.myenum.TypeWater;
import ru.systemairac.calculator.service.allinterface.PDDocumentService;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PDDocumentServiceTest {

    @Autowired
    private PDDocumentService service;

    @Test
    void toPDDocument() throws IOException {
        ProjectDto projectDto = ProjectDto.builder()
                .title("Офисный комплекс \"Мечта\"")
                .address("Российская Федерация, г. Москва, " +
                        "Ленинский пр-т, стр. 123")
                .build();

        TechDataDto techDataDto = TechDataDto.builder()
                .voltage(220)
                .width(123)
                .typeCylinder(TypeCylinder.DISMOUNTABLE_CYLINDER)
                .typeWater(TypeWater.TAP_WATER)
                .tempIn(20.4)
                .typeMontage(TypeMontage.AHU)
                .length(2233)
                .humOut(100)
                .phase(1)
                .humIn(0)
                .enumHumidifierType(EnumHumidifierType.HEATING_ELEMENT)
                .calcCapacity(123)
                .airFlow(123)
                .build();

        UserDto userDto = UserDto.builder()
                .fullName("Иванов Иван Иванович")
                .addressCompany("г. Москва, ул. Академика Королёва, д. 12")
                .email("mkfgops@rdgijf.dfg")
                .position("Директор всего")
                .nameCompany("ООО Вектор")
                .phone(123465354L)
                .build();

        try (PDDocument document = service.toPDDocument(userDto, projectDto, techDataDto)) {

        }
    }
}
