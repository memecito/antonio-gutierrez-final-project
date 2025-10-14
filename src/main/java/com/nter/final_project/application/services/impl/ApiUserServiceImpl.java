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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserServiceImpl implements ApiUserService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapped apiUserMapped;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final CountryService countryService;


    /***
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<ApiUser> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return apiUserRepository.findAll(pageable);
    }

    @Override
    public Page<ApiUser> getActive(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return apiUserRepository.findByActiveTrue(pageable);
    }

    /***
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    public ApiUser getById(Long id, String token) {
        ApiUser userfound = getById(id);
        jwtService.authorization(id, token.substring(7));
        return userfound;
    }

    /***
     *
     * @param id
     * @return
     */
    @Override
    public ApiUser getById(Long id) {
        return apiUserRepository.findById(id).orElseThrow(
                () -> new UserNotFounException("Usuario con id: " + id + " no encontrado, APS02")
        );
    }

    /***
     *
     * @param name
     * @return
     */
    @Override
    public List<ApiUser> getByName(String name) {
        return apiUserRepository.findByFullName(name).orElseThrow(
                () -> new EntityNotFoundException("No se ha encontrado ningun usuario con ese nombre, APS03")
        );
    }

    /***
     *
     * @param email
     * @return
     */
    @Override
    public ApiUser getByEmail(String email) {
        return apiUserRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.warn("User Not Found: {}", email);
                    return new EntityNotFoundException("No se ha encontrado ningun usuario con ese nombre, APS04");
                }
        );
    }

    /***
     *
     * @param apiUser
     * @return
     */
    @Override
    @Transactional
    public ApiUser created(ApiUser apiUser) {
        if (apiUserRepository.findByEmail(apiUser.getEmail()).isPresent()) {
            log.warn("Created: Email in use: {}", apiUser.getEmail());
            throw new EmailAlreadyExistException("este email ya esta registrado, APS05");
        }

        apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        log.info("usuario creado");
        return apiUserRepository.save(apiUser);
    }

    /***
     *
     * @param id
     * @param apiUser
     * @return
     */
    @Override
    @Transactional
    public ApiUser update(Long id, ApiUser apiUser) {
        ApiUser userFound = getById(id);
        if (!Objects.equals(userFound.getEmail(), apiUser.getEmail()))
            throw new BadRequestException("No se puede cambiar el email, APS06");
        if (!Objects.equals(null, apiUser.getPassword()))
            apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        return apiUserMapped.update(userFound, apiUser);
    }

    /***
     *
     * @param id
     * @param country
     * @return
     */
    @Override
    @Transactional
    public ApiUser updateCountry(Long id, Country country) {
        Country countryFound = countryService.getByCode(country.getCode());
        ApiUser userFound = getById(id);
        userFound.setCountry(countryFound);
        return userFound;
    }

    @Override
    @Transactional
    public ApiUser updateAdmin(Long id) {
        ApiUser userFound = getById(id);
        userFound.setAdmin(true);
        return userFound;
    }

    @Override
    @Transactional
    public ApiUser downgroudnAdmin(Long id) {
        ApiUser userFound = getById(id);
        userFound.setAdmin(false);
        return userFound;
    }

    /***
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ApiUser statusDesactive(Long id) {
        ApiUser userFound = getById(id);
        userFound.setActive(false);
        return apiUserRepository.save(userFound);
    }

    ///*
    ///
    /// @param id
    /// @return
    @Override
    @Transactional
    public ApiUser statusActived(Long id) {
        ApiUser userFound = getById(id);
        userFound.setActive(true);
        return apiUserRepository.save(userFound);
    }

    /***
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleted(Long id) {
        ApiUser userFound = getById(id);
        userFound.setActive(false);
        apiUserRepository.save(userFound);
    }
}
