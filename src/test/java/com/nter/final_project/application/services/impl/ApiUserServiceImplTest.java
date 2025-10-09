package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EmailAlreadyExistException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UserNotFounException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.persistence.repository.ApiUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiUserServiceImplTest {

    @Mock
    private ApiUserRepository apiUserRepository;
    @Mock
    private JwtService jwtService;

    @Mock
    private ApiUserMapped apiUserMapped;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ApiUserServiceImpl apiUserService;

    @Test
    void getAll() {
        //Given
        ApiUser apiUser= DataProviders.userMock();
        Page<ApiUser> apiUsers = DataProviders.pageApiUserMock();
        Pageable pageable = PageRequest.of(0, 5);

        //When
        when(apiUserRepository.findAll(pageable)).thenReturn(apiUsers);
        Page<ApiUser> apiUserPage = apiUserService.getAll(0, 5);
        //Then
        assertNotNull(apiUserPage);
        assertFalse(apiUserPage.isEmpty());
    }

    @Test
    void getById() {

        Long id = 1L;

        when(apiUserRepository.findById(anyLong())).thenReturn(DataProviders.userOptionalMock());

        ApiUser apiUser = apiUserService.getById(id);

        assertNotNull(apiUser);
        verify(apiUserRepository).findById(anyLong());
    }

    @Test
    void getByIdException() {
        String mensaje = "Usuario con id: " + 1L + " no encontrado, APS02";

        Exception exception = assertThrows(UserNotFounException.class,
                () -> apiUserService.getById(1L));

        assertEquals(mensaje, exception.getMessage());
    }

    @Test
    void testGetById() {
        Long id = 1L;
        String token = DataProviders.tokenMock();

        when(apiUserRepository.findById(anyLong())).thenReturn(DataProviders.userOptionalMock());
        when(jwtService.authorization(anyLong(), anyString())).thenReturn(true);
        ApiUser apiUser = apiUserService.getById(id, token);

        boolean auth = jwtService.authorization(id, token);

        assertNotNull(apiUser);
        verify(apiUserRepository).findById(anyLong());

        assertTrue(auth);
    }

    @Test
    void getByName() {
        String fullName = "nombre";
        when(apiUserRepository.findByFullName(any(String.class))).thenReturn(DataProviders.userOptionalListMock());

        List<ApiUser> userResult = apiUserService.getByName(fullName);
        assertNotNull(userResult);
        verify(apiUserRepository).findByFullName(any(String.class));
    }

    @Test
    void getByNameException() {
        String mensaje = "No se ha encontrado ningun usuario con ese nombre, APS03";

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> apiUserService.getByName(any(String.class)));

        assertEquals(mensaje, exception.getMessage());

    }

    @Test
    void getByEmail() {
        String email = "email";
        when(apiUserRepository.findByEmail(any(String.class))).thenReturn(DataProviders.userOptionalMock());

        ApiUser userResult = apiUserService.getByEmail(email);
        assertNotNull(userResult);
        verify(apiUserRepository).findByEmail(any(String.class));
    }

    @Test
    void getByEmailException() {
        String message = "No se ha encontrado ningun usuario con ese nombre, APS04";
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> apiUserService.getByEmail(any(String.class)));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void created() {
        ApiUser userIn = DataProviders.userMock();
        userIn.setEmail("correo@correo.com");
        userIn.setPassword("password");
        when(apiUserRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString())).thenReturn("encoderr");

        when(apiUserRepository.save(any(ApiUser.class)))
                .thenReturn(userIn);

        ApiUser userResult = apiUserService.created(userIn);

        Assertions.assertEquals(userIn.getEmail(), userResult.getEmail());

    }

    @Test
    void createdException() {
        Optional<ApiUser> oldUser = Optional.of(new ApiUser());
        ApiUser newUser = DataProviders.userMock();

        newUser.setEmail("test@example.com");
        String message = "este email ya esta registrado, APS05";

        when(apiUserRepository.findByEmail(any(String.class))).thenReturn(oldUser);

        Exception exception = assertThrows(EmailAlreadyExistException.class, () -> {
            apiUserService.created(newUser);
        });

        assertEquals(message, exception.getMessage());

    }

    @Test
    void update() {
        Long id = 1L;
        ApiUser userUpdate = DataProviders.userMock();

        when(apiUserRepository.findById(id)).thenReturn(Optional.of(userUpdate));

        when(apiUserMapped.update(any(ApiUser.class), any(ApiUser.class))).thenReturn(userUpdate);

        ApiUser userResult = apiUserService.update(id, userUpdate);

        assertNotNull(userResult);
        verify(apiUserMapped).update(any(ApiUser.class), any(ApiUser.class));

    }

    @Test
    void updateException() {
        Long id = 1L;
        String message = "No se puede cambiar el email, APS06";

        ApiUser oldUser = DataProviders.userMock();
        oldUser.setId(id);
        oldUser.setEmail("original@mail.com");
        ApiUser newUser = DataProviders.userMock();
        newUser.setEmail("new@email.com");

        when(apiUserRepository.findById(anyLong())).thenReturn(Optional.of(oldUser));

        Exception exception = assertThrows(BadRequestException.class,
                () -> apiUserService.update(id, newUser));

        assertEquals(message, exception.getMessage());
    }


    @Test
    void updateCountry() {
        /*
        Long id = 1L;
        String code = "ES";
        Country newCountry = new Country(code, "España");
        ApiUser user = DataProviders.userMock();
        user.setId(id);
        when(apiUserRepository.findById(anyLong())).thenReturn(Optional.of(user));

        ApiUser userResult = apiUserService.updateCountry(id, newCountry);
        assertNotNull(userResult);*/
    }

    @Test
    void updateAdmin() {

        Long id = 1L;
        ApiUser user = DataProviders.userMock();
        user.setAdmin(true);

        when(apiUserRepository.findById(id)).thenReturn(Optional.of(user));

        ApiUser userResult = apiUserService.updateAdmin(id);

        assertNotNull(userResult);
        assertTrue(user.getAdmin());
    }

    @Test
    void downgroudnAdmin() {
        Long id = 1L;
        ApiUser user = DataProviders.userMock();
        user.setAdmin(false);

        when(apiUserRepository.findById(id)).thenReturn(Optional.of(user));

        ApiUser userResult = apiUserService.downgroudnAdmin(id);

        assertNotNull(userResult);
        assertFalse(user.getAdmin());
    }

    @Test
    void statusDesactive() {
        Long id = 1L;
        ApiUser user = DataProviders.userMock();
        user.setActive(false);

        when(apiUserRepository.findById(id)).thenReturn(Optional.of(user));
        when(apiUserRepository.save(user)).thenReturn(user);

        ApiUser userResult = apiUserService.statusDesactive(id);

        assertNotNull(userResult);
    }


    @Test
    void statusActived() {
        Long id = 1L;
        ApiUser user = DataProviders.userMock();
        user.setActive(true);

        when(apiUserRepository.findById(id)).thenReturn(Optional.of(user));
        when(apiUserRepository.save(user)).thenReturn(user);

        ApiUser userResult = apiUserService.statusActived(id);

        assertNotNull(userResult);
        assertTrue(user.getActive());
    }

    @Test
    void deleted() {
        Long id = 1L;
        ApiUser user = DataProviders.userMock();
        user.setActive(false);

        when(apiUserRepository.findById(id)).thenReturn(Optional.of(user));
        when(apiUserRepository.save(user)).thenReturn(user);


        apiUserService.deleted(id);
        assertFalse(user.getActive());

    }
}