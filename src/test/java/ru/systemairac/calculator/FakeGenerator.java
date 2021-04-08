package ru.systemairac.calculator;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.dto.VaporDistributorDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.myenum.TypeMontage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FakeGenerator {

    private final Random random = new Random();
    private final Faker faker= new Faker(new Locale("ru"), new RandomService());

    public TechDataDto fakeTechDataDto() {
        return TechDataDto.builder()
                .airFlow(random.nextInt(300))
                .calcCapacity(random.nextInt(300)) // а это не правда
                .enumHumidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .humIn(random.nextInt(100))
                .humOut(random.nextInt(100))
                .tempIn(random.nextInt(80) - 40)
                .typeMontage(TypeMontage.values()[random.nextInt(TypeMontage.values().length)])
                .voltage(EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)])
                .width(random.nextInt(1000))
                .length(random.nextInt(1000))
                .build();
    }

    public TechData fakeTechData() {
        Random random = new Random();
        return TechData.builder()
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
                .length(random.nextInt(1000))
                .build();
    }

    public HumidifierDto fakeGoodHumidifierDto() {
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

    public Humidifier fakeGoodHumidifier() {
        BigDecimal price = BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01);
        price = price.setScale(2, RoundingMode.FLOOR); // TODO: а как это происходит в БД?
        return Humidifier.builder()
                .id(null)
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
                .price(price) // от 0 до 1_000_000
                .build();
    }

    public List<Humidifier> fakeListOfGoodHumidifiers(int number) {
        List<Humidifier> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(fakeGoodHumidifier());
            Humidifier humidifier = list.get(i);
            humidifier.setArticleNumber(i + "_" + humidifier.getArticleNumber()); // нужно, так как у нас
        }
        return list;
    }

    public User fakeGoodUser(PasswordEncoder encoder) {
        User user = new User();
//        user.setId(null);
        user.setEmail(faker.bothify("?#?#?#?#?#@example.com"));
        user.setFullName(faker.name().fullName());
        user.setPassword(encoder.encode(faker.regexify("[A-Za-z0-9]{8,20}")));
        user.setNameCompany(faker.company().name());
        user.setPost(faker.address().fullAddress());
        user.setPhone(74951234567L);
//        user.setRole()
        user.setProjects(new ArrayList<>());
        return user;
    }

    public UserDto fakeGoodUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(faker.bothify("?#?#?#?#@example.com"));
        String pw = faker.regexify("[A-Za-z0-9]{8,20}");
        userDto.setPassword(pw);
        userDto.setMatchingPassword(pw);

        return userDto;
    }

    public VaporDistributorDto fakeVaporDistributorDto() {
        return VaporDistributorDto.builder()
            .length(1000)
            .articleNumber("sdfs")
            .diameter(25)
            .price(new BigDecimal(100))
            .build();
    }
}
