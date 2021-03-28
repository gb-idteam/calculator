package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;

@Mapper(uses = {ProjectMapper.class})
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "projects", target = "projects")
    User toUser(UserDto dto);

    @InheritInverseConfiguration
    UserDto fromUser(User user);

}
