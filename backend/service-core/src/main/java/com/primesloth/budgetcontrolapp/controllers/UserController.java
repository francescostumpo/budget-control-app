package com.primesloth.budgetcontrolapp.controllers;

import com.primesloth.budgetcontrolapp.api.model.User;
import com.primesloth.budgetcontrolapp.api.rest.UsersApi;
import com.primesloth.budgetcontrolapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public ResponseEntity<User> createUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Integer id) {
        return UsersApi.super.deleteUser(id);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }


    @Override
    public ResponseEntity<User> getUserById(Integer id) {
        return UsersApi.super.getUserById(id);
    }

    @Override
    public ResponseEntity<User> updateUser(Integer id, User user) {
        return UsersApi.super.updateUser(id, user);
    }
}
