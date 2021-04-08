package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    private final FakeGenerator fakeGenerator = new FakeGenerator();
    private final UserMapper mapper = UserMapper.MAPPER;

    // TODO: а что нам делать с шифрованием пароля?
    @Disabled
    @RepeatedTest(5)
    void toUser() {
    }

    @Disabled
    @RepeatedTest(5)
    void fromUser() {
    }
}
