package com.matekeszi.authentication.view;

import com.matekeszi.authentication.domain.UserEntity;
import com.matekeszi.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable("id") final long userId){
        return userService.findById(userId);
    }
}