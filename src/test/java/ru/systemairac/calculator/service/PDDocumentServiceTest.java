package ru.systemairac.calculator.service;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.service.allinterface.FileService;
import ru.systemairac.calculator.service.allinterface.PDDocumentService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.UserService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

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

    @BeforeAll
    static void init() {
        faker = new Faker(new Locale("ru"), new RandomService());
        random = new Random();
    }

    @Test
    void toPDDocument() throws IOException {
        ProjectDto projectDto = ProjectDto.builder()
                .title("Офисный комплекс \"Мечта\"")
                .address("Российская Федерация, г. Москва, " +
                        "Ленинский пр-т, стр. 123")
                .build();

        TechDataDto techDataDto = TechDataDto.builder()
                .voltage(EnumVoltageType.ONE_PHASE_220V)
                .width(123)
                .tempIn(20.4)
                .typeMontage(TypeMontage.AHU)
                .length(2233)
                .humOut(100)
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
        VaporDistributorDto distributorDto  = VaporDistributorDto.builder()
                .length(1000)
                .articleNumber("sdfs")
                .diameter(25)
                .price(new BigDecimal(100))
                .build();

        try (PDDocument document = service.toPDDocument(userDto, projectDto, techDataDto, humidifierDto, new ArrayList<>(),distributorDto)) {
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
                .voltage(EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)]) // из списка
                .numberOfCylinders(1 + random.nextInt(3)) // от 1 до 3
                .vaporPipeDiameter(random.nextInt(31) + 15) // от 15 до 45
                .price(BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01)) // от 0 до 1_000_000
                .build();
    }
}
