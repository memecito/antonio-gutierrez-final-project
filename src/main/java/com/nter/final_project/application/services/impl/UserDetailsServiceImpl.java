package com.nter.final_project.application.services.impl;

import com.nter.final_project.exception.UserNotFounException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.repository.ApiUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApiUserRepository apiUserRepository;
    @Override
    public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {

        ApiUser user= apiUserRepository.findByEmail(usermail).orElseThrow(
                ()-> new UserNotFounException("Usuario no encontrado, UDS01")
        );

        return User.builder()
                .username(user.getFullName())
                .password(user.getPassword())
                .roles(getRoles(user.getAdmin()))
                .build();
    }

    private String[] getRoles(boolean rol) {
        if (rol)
            return new String []{"ADMIN","USER"};

        return new String[]{"USER"};
    }
}
