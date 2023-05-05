package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.PersonResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPersonResponseMapper {
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.surname", target = "surname")
    @Mapping(source = "person.mail", target = "mail")
    @Mapping(source = "person.phone", target = "phone")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.idDniType", target = "idDniType")
    @Mapping(source = "person.dniNumber", target = "dniNumber")
    @Mapping(source = "person.idPersonType", target = "idPersonType")
    PersonResponseDto userToPersonResponse(User user);
    List<PersonResponseDto> userListToPersonResponseList(List<User> userList);
}
