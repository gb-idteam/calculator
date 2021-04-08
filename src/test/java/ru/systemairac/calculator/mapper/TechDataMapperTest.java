package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.Test;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.dto.TechDataDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TechDataMapperTest {

    private final TechDataMapper mapper = TechDataMapper.MAPPER;

    private final FakeGenerator fakeGenerator = new FakeGenerator();

    @Test
    void toTechData() {
        for (int i = 0; i < 100; i++) {
            TechDataDto techDataDto = fakeGenerator.fakeTechDataDto();
            TechData techData = mapper.toTechData(techDataDto);
            assertFieldsEqual(techDataDto, techData);
        }
    }

    private void assertFieldsEqual(TechDataDto techDataDto, TechData techData) {
        assertEquals(techDataDto.getAirFlow(), techData.getAirFlow());
        assertEquals(techDataDto.getCalcCapacity(), techData.getCalcCapacity());
        assertEquals(techDataDto.getHumIn(), techData.getHumIn());
        assertEquals(techDataDto.getHumOut(), techData.getHumOut());
        assertEquals(techDataDto.getLength(), techData.getLength());
        assertEquals(techDataDto.getEnumHumidifierType(), techData.getEnumHumidifierType());
        assertEquals(techDataDto.getTempIn(), techData.getTempIn());
        assertEquals(techDataDto.getTypeMontage(), techData.getTypeMontage());
        assertEquals(techDataDto.getVoltage(), techData.getVoltage());
        assertEquals(techDataDto.getWidth(), techData.getWidth());
    }

    @Test
    void fromTechData() {
        for (int i = 0; i < 100; i++) {
            TechData techData = fakeGenerator.fakeTechData();
            TechDataDto techDataDto = mapper.fromTechData(techData);
            assertFieldsEqual(techDataDto, techData);
        }
    }
}
