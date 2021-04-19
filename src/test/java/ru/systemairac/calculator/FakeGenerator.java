package ru.systemairac.calculator;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.myenum.HumidifierComponentType;
import ru.systemairac.calculator.myenum.TypeMontage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class FakeGenerator {

    private final Random random = new Random();
    private final Faker faker = new Faker(new Locale("ru"), new RandomService());
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private long nextId = 1;

    public long fakeId() {
        return nextId++;
    }


    private static final BigDecimal TWO = BigDecimal.valueOf(2);

    /**
     * @param minPrice Минимальная цена, в копейках.
     * @param maxPrice Максимальная цена, в копейках.
     */
    public BigDecimal fakePrice(int minPrice, int maxPrice) { // TODO: проверить метод. То ли я делаю вообще??
        BigDecimal price = BigDecimal.valueOf(random.nextInt(maxPrice - minPrice) + minPrice);
        price = price.divide(TWO, RoundingMode.FLOOR);
        price = price.setScale(2, RoundingMode.FLOOR); // TODO: а как это происходит в БД?
        return price;
    }

    public TechDataDto fakeTechDataDto() {
        return TechDataDto.builder()
                .id(fakeId())
                .airFlow(random.nextInt(300))
                .calcCapacity(random.nextInt(300)) // а это не правда
                .enumHumidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .humIn((double) random.nextInt(100))
                .humOut((double) random.nextInt(100))
                .tempIn((double) (random.nextInt(80) - 40))
                .typeMontage(TypeMontage.values()[random.nextInt(TypeMontage.values().length)])
                .voltage(EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)])
                .width(random.nextInt(1000))
                .height(random.nextInt(1000))
                .build();
    }

    public TechData fakeTechData() {
        return TechData.builder()
                .id(fakeId()) // id обязательно > 0
                .airFlow(random.nextInt(300))
                .calcCapacity(random.nextInt(300)) // а это не правда
                .enumHumidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .id(random.nextLong())
                .humIn(random.nextInt(100))
                .humOut(random.nextInt(100))
                .tempIn(random.nextInt(80) - 40)
                .typeMontage(TypeMontage.values()[random.nextInt(TypeMontage.values().length)])
                .voltage(EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)])
                .width(random.nextInt(1000))
                .height(random.nextInt(1000))
                .build();
    }

    public HumidifierDto fakeHumidifierDto() {
        return HumidifierDto.builder()
                .id(fakeId())
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

    public Humidifier fakeHumidifier() {
        return Humidifier.builder()
                .id(fakeId())
                .articleNumber(faker.bothify("???###")) // должен быть Unique, вообще-то
                .brand(null) // TODO: пока без бренда
                .humidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .electricPower(random.nextDouble() * 90) // от 0 до 90, не зависит от capacity
                .capacity(random.nextDouble() * 120) // от 0 до 120
                .voltage(EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)]) // из списка
                .numberOfCylinders(1 + random.nextInt(3)) // от 1 до 3
                .vaporPipeDiameter(random.nextInt(31) + 15) // от 15 до 45
                .vaporDistributors(null) // TODO: пока без парораспределителей
                .humidifierComponents(null) // TODO: пока без компонентов
                .price(fakePrice(0, 50_000_000)) // от 0 до 500_000 рублей
                .build();
    }

    public List<Humidifier> fakeHumidifierList(int number) {
        List<Humidifier> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(fakeHumidifier());
            Humidifier humidifier = list.get(i);
            humidifier.setArticleNumber(i + "_" + humidifier.getArticleNumber()); // нужно, так как у нас
        }
        return list;
    }

    public User fakeUser() {
        User user = new User();
        user.setId(fakeId());
        user.setEmail(faker.bothify("?#?#?#?#?#@example.com"));
        user.setFullName(faker.name().fullName());
        user.setPassword(this.encoder.encode(faker.regexify("[A-Za-z0-9]{8,20}")));
        user.setNameCompany(faker.company().name());
        user.setPosition(faker.address().fullAddress());
        user.setPhone(74951234567L);
//        user.setRole()
        user.setProjects(new ArrayList<>());
        return user;
    }

    public UserDto fakeUserDto() {
        String pw = faker.internet().password(8, 40);
        // Проекты тут не добавляем
        return UserDto.builder()
                .password(pw)
                .fullName(faker.name().fullName())
                .nameCompany(faker.company().name())
                .addressCompany(faker.address().fullAddress())
                .position(faker.company().profession())
                .phone(74951234567L)
                .email(faker.internet().emailAddress())
                .matchingPassword(pw)
                .build();
    }

    public VaporDistributor fakeVaporDistributor() {
        return VaporDistributor.builder()
                .id(fakeId())
                .length(random.nextInt(1000) + 400)
                .articleNumber(faker.bothify("D##############"))
                .diameter(random.nextInt(25) + 10)
                .price(fakePrice(0, 50_000_000))
                .image(null) // TODO
                .build();
    }

    public VaporDistributorDto fakeVaporDistributorDto() {
        return VaporDistributorDto.builder()
                .length(random.nextInt(1000) + 400)
                .articleNumber(faker.bothify("D##############"))
                .diameter(random.nextInt(25) + 10)
                .price(fakePrice(0, 50_000_000))
                .image(null) // TODO
                .build();
    }

    public Project fakeProject(User user) {
        return Project.builder()
                .id(fakeId())
                .title(faker.commerce().productName())
                .address(faker.address().fullAddress())
                .user(user)
                .calculations(null)
                .build();
    }

    public EstimateDto fakeEstimateDto() {
        return EstimateDto.builder()
                .humidifier(fakeHumidifier())
                .humidifierComponents(Collections.singletonList(fakeHumidifierComponent()))
                .vaporDistributor(fakeVaporDistributor())
                .build();
    }

    public ProjectDto fakeProjectDto() {
        return ProjectDto.builder()
                .id(fakeId())
                .title(faker.commerce().productName())
                .address(faker.address().fullAddress())
                .build();
    }

    public HumidifierComponent fakeHumidifierComponent() {
        return HumidifierComponent.builder()
                .id(fakeId())
                .articleNumber(faker.bothify("D##############"))
                .type(HumidifierComponentType.values()[random.nextInt(HumidifierComponentType.values().length)])
                .image(null)
                .optional(random.nextBoolean())
                .brand(null)
                .price(fakePrice(0, 50_000_000))
                .build();
    }

    public HumidifierComponentDto fakeHumidifierComponentDto() {
        return HumidifierComponentDto.builder()
                .id(fakeId())
                .articleNumber(faker.bothify("D##############"))
                .type(HumidifierComponentType.values()[random.nextInt(HumidifierComponentType.values().length)])
                .image(null)
                .optional(random.nextBoolean())
                .price(fakePrice(0, 50_000_000))
                .build();
    }
}
