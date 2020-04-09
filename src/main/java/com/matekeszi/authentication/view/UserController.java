package com.matekeszi.authentication.view;

import com.matekeszi.authentication.domain.UserEntity;
import com.matekeszi.authentication.exception.UserNotFoundException;
import com.matekeszi.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable("id") final long userId) {
        UserEntity userEntity = userService.findById(userId);
        if (userEntity == null) throw new UserNotFoundException("There is no user found with id: "+ userId);
        else return userEntity;
    }

    @PostMapping()
    public void register(@RequestBody UserEntity userEntity){
        userService.register(userEntity);
    }

    @GetMapping()
    public Iterable<UserEntity> findAll(){
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") final long userId) {
        userService.deleteById(userId);
    }
}