package com.example.test_telros.user.mapper;

import com.example.test_telros.photo.service.PhotoStorageService;
import com.example.test_telros.user.dto.UserContactDTO;
import com.example.test_telros.user.dto.UserDetailsDTO;
import com.example.test_telros.user.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserContactDTO toContactDto(User user);
    User toUser(UserContactDTO dto);

    @Mapping(target = "photoUrl", expression = "java(photoStorageService.getPublicUrl(user.getPhotoKey()))")
    UserDetailsDTO toDetailsDto(User user, @Context PhotoStorageService photoStorageService);

    @Mapping(target = "photoKey", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserDetailsDTO dto);
}
