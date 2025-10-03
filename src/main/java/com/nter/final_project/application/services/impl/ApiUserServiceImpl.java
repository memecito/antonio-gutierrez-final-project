package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.application.services.CountryService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EmailAlreadyExistException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UserNotFounException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.persistence.repository.ApiUserRepository;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiUserServiceImpl implements ApiUserService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapped apiUserMapped;

    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    private final JwtService jwtService;


    @Override
    public Page<ApiUser> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return apiUserRepository.findAll(pageable);
    }

    @Override
    public ApiUser getById(Long id) {

        return apiUserRepository.findById(id).orElseThrow(
                () -> new UserNotFounException("Usuario con id: " + id + " no encontrado, APS01")
        );
    }

    @Override
    public List<ApiUser> getByName(String name) {
        return apiUserRepository.findByFullName(name).orElseThrow(
                () -> new EntityNotFoundException("No se ha encontrado ningun usuario con ese nombre, APS03")
        );
    }

    @Override
    public ApiUser getByEmail(String email) {
        return apiUserRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("No se ha encontrado ningun usuario con ese nombre, APS04")
        );
    }

    @Override
    public AuthToken sigin(AuthToken userToken) {
        return null;
    }
    @Override
    public AuthToken autentificate(ApiUser user) {
        ApiUser userFound= getByEmail(user.getEmail());
        if(!passwordEncoder.matches(user.getPassword(), userFound.getPassword()))
            throw new BadRequestException("Invalid credentails, APS07");
        UserDetails userDetails= userDetailsService.loadUserByUsername(userFound.getEmail());

        return new AuthToken(
                jwtService.generateAccessToken(userDetails)
        );
    }

    @Override
    @Transactional
    public AuthToken register(ApiUser user) {

        ApiUser userRegister= created(user);
        UserDetails userDetails= userDetailsService.loadUserByUsername(userRegister.getEmail());

        return new AuthToken(
                jwtService.generateAccessToken(userDetails)
        );
    }

    @Override
    @Transactional
    public ApiUser created(ApiUser apiUser) {
        if (apiUserRepository.findByEmail(apiUser.getEmail()).isPresent())
            throw new EmailAlreadyExistException("este email ya esta registrado, APS05");

        apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));

        return apiUserRepository.save(apiUser);
    }

    @Override
    @Transactional
    public ApiUser update(Long id, ApiUser apiUser) {
        ApiUser userFound= getById(id);
        if(!Objects.equals(userFound.getEmail(),apiUser.getEmail()))
            throw new BadRequestException("No se puede cambiar el email, APS06");
       return apiUserMapped.update(userFound, apiUser) ;
    }

    @Override
    public ApiUser updateCountry(Long id, Country country) {
        Country countryFound= countryService.getByCode(country.getCode());
        ApiUser userFound= getById(id);
        userFound.setCountry(countryFound);
        return apiUserRepository.save(userFound);
    }

    @Override
    public ApiUser statusDesactive(Long id) {
        ApiUser userFound= getById(id);
        userFound.setActive(false);
        return apiUserRepository.save(userFound);
    }

    @Override
    public ApiUser statusActived(Long id) {
        ApiUser userFound= getById(id);
        userFound.setActive(true);
        return apiUserRepository.save(userFound);    }




    @Override
    @Transactional
    public void deleted(Long id) {
        ApiUser userFound= getById(id);
        userFound.setActive(false);
        apiUserRepository.save(userFound);
    }
}
