package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.persistence.entity.ApiUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Lazy
    @Autowired
    private ApiUserService apiUserService;

    //cambios anteriores al fallo
    //private  final ApiUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {

        ApiUser user = apiUserService.getByEmail(usermail);
        /*
        ApiUser user = apiUserRepository.findByEmail(usermail).orElseThrow(
                () -> new UserNotFounException("Usuario no encontrado, UDS01")
        );

         */

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(getRoles(user.getAdmin()))
                .build();
    }

    private String[] getRoles(boolean rol) {
        if (rol)
            return new String[]{"ADMIN", "USER"};

        return new String[]{"USER"};
    }
}
