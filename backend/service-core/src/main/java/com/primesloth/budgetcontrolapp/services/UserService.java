package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.User;
import com.primesloth.budgetcontrolapp.mappers.UserMapper;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrganizationRepository organizationRepository;

    public ResponseEntity<List<User>> getAllUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(userMapper.toUsersListDto(users));
    }

    public ResponseEntity<User> createUser(User user) {
        if(Objects.isNull(user.getOrganization()) ||
                StringUtils.isBlank(String.valueOf(user.getOrganization().getId()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organization id parameter.");
        }

        var orgOptional = organizationRepository.findById(Long.valueOf(user.getOrganization().getId()));
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }
        var userEntity = userMapper.toUserEntity(user);
        userEntity.setOrganizationEntity(orgOptional.get());
        var u = userRepository.save(userEntity);
        return ResponseEntity.ok(userMapper.toUserDto(u));
    }
}
