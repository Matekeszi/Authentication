package com.matekeszi.authentication.configuration;

import com.matekeszi.authentication.domain.UserEntity;
import com.matekeszi.authentication.domain.UserRoles;
import com.matekeszi.authentication.exception.UserNotFoundException;
import com.matekeszi.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException("User not found with username: " + username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRoles role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoles().name()));
        }
        return User.withDefaultPasswordEncoder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities).build();
    }
}
