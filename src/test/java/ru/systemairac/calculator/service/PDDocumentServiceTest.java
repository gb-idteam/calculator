package ru.systemairac.calculator.service;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeCylinder;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.myenum.TypeWater;
import ru.systemairac.calculator.service.allinterface.FileService;
import ru.systemairac.calculator.service.allinterface.PDDocumentService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.UserService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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

    private static Faker faker;
    private static Random random;
    private static Integer[] possibleVoltages;

    @BeforeAll
    static void init() {
        faker = new Faker(new Locale("ru"), new RandomService());
        random = new Random();
        possibleVoltages = new Integer[]{220, 380};
    }

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

        HumidifierDto humidifierDto = fakeGoodHumidifierDto();

        try (PDDocument document = service.toPDDocument(userDto, projectDto, techDataDto, humidifierDto, new ArrayList<>())) {
//            document.save("123.pdf");
        }
    }

    private HumidifierDto fakeGoodHumidifierDto() {
        return HumidifierDto.builder()
                .id(null)
                .articleNumber(faker.bothify("???###")) // должен быть Unique, вообще-то
//                .brand(null) // TODO: пока без бренда
                .humidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .electricPower(random.nextDouble() * 90) // от 0 до 90, не зависит от capacity
                .capacity(random.nextDouble() * 120) // от 0 до 120
                .phase(random.nextInt(2) * 2 + 1) // 1 или 3
                .voltage(possibleVoltages[random.nextInt(possibleVoltages.length)]) // из списка
                .numberOfCylinders(1 + random.nextInt(3)) // от 1 до 3
                .vaporPipeDiameter(random.nextInt(31) + 15) // от 15 до 45
                .price(BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01)) // от 0 до 1_000_000
                .build();
    }
}
