package com.matekeszi.authentication.view;

import com.matekeszi.authentication.configuration.UserDetailsServiceImpl;
import com.matekeszi.authentication.domain.TokenEntity;
import com.matekeszi.authentication.domain.UserEntity;
import com.matekeszi.authentication.exception.UserNotFoundException;
import com.matekeszi.authentication.repository.TokenRepository;
import com.matekeszi.authentication.service.UserService;
import com.matekeszi.authentication.token.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenRepository tokenRepository;

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable("id") final long userId) {
        UserEntity userEntity = userService.findById(userId);
        if (userEntity == null) throw new UserNotFoundException("There is no user found with id: " + userId);
        else return userEntity;
    }

    @PostMapping()
    public void register(@RequestBody UserEntity userEntity) {
        userService.register(userEntity);
    }

    @GetMapping()
    public Iterable<UserEntity> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") final long userId) {
        userService.deleteById(userId);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody UserEntity userEntity) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        String token = TokenGenerator.createToken(userDetailsService.loadUserByUsername(userEntity.getUsername()));
        tokenRepository.save(TokenGenerator.createTokenEntity(token));
        return token;
    }
}