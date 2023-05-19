package com.example.antifraud.controller;


import com.example.antifraud.controller.request.ChangeRoleRequest;
import com.example.antifraud.controller.request.ChangeStatusRequest;
import com.example.antifraud.controller.response.StatusChangeResponse;
import com.example.antifraud.controller.response.UserDeleteResponse;
import com.example.antifraud.repository.entity.User;
import com.example.antifraud.repository.entity.View;
import com.example.antifraud.service.AuthorizationService;
import com.example.antifraud.service.UserService;
import com.example.antifraud.service.dto.UserRole;
import com.example.antifraud.util.AntiFraudException;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Validated
public class UserController {
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @PostMapping("/user")
    @JsonView(View.UserView.class)
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @DeleteMapping("/user/")
    public ResponseEntity<UserDeleteResponse> delete() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<UserDeleteResponse> delete(@PathVariable String username, HttpServletRequest request) {
        try {
            authorizationService.validateEntry(request.getUserPrincipal(), UserRole.ADMINISTRATOR);
        } catch (AntiFraudException e) {
            return new ResponseEntity<>(e.getException().getStatusCode());
        }
        return new ResponseEntity<>(userService.delete(username), HttpStatus.OK);
    }

    @GetMapping("/list")
    @JsonView(View.UserView.class)
    public ResponseEntity<List<User>> list(HttpServletRequest request) {
        try {
            authorizationService.validateEntry(request.getUserPrincipal(), List.of(UserRole.ADMINISTRATOR, UserRole.SUPPORT));
        } catch (AntiFraudException e) {
            return new ResponseEntity<>(e.getException().getStatusCode());
        }
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @PutMapping("/role")
    @JsonView(View.UserView.class)
    public ResponseEntity<User> changeRole(@Valid @RequestBody ChangeRoleRequest changeUserRoleRequest, HttpServletRequest request) {
        try {
            authorizationService.validateEntry(request.getUserPrincipal(), UserRole.ADMINISTRATOR);
        } catch (AntiFraudException e) {
            return new ResponseEntity<>(e.getException().getStatusCode());
        }
        return new ResponseEntity<>(userService.changeRole(changeUserRoleRequest), HttpStatus.OK);
    }

    @PutMapping("/access")
    public ResponseEntity<StatusChangeResponse> changeStatus(@Valid @RequestBody ChangeStatusRequest changeUserStatusRequest, HttpServletRequest request) {
        try {
            authorizationService.validateEntry(request.getUserPrincipal(), UserRole.ADMINISTRATOR);
        } catch (AntiFraudException e) {
            return new ResponseEntity<>(e.getException().getStatusCode());
        }
        return new ResponseEntity<>(userService.changeStatus(changeUserStatusRequest), HttpStatus.OK);
    }
}