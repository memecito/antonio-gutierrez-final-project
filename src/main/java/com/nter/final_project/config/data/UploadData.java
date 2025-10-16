package com.nter.final_project.config.data;

import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.repository.ApiUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UploadData implements CommandLineRunner {

    private final ApiUserRepository apiUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createAdminUser();
    }

    private void createAdminUser() {
        String adminEmail = "admin@example.com";

        // 1. Comprobar si el usuario ya existe para no crearlo de nuevo
        Optional<ApiUser> existingAdmin = apiUserRepository.findByEmail(adminEmail);

        if (existingAdmin.isEmpty()) {
            // 2. Si no existe, crear el nuevo usuario
            ApiUser adminUser = new ApiUser();
            adminUser.setFullName("Administrador del Sistema");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("admin1234"));
            adminUser.setAdmin(true);
            adminUser.setActive(true);
            apiUserRepository.save(adminUser);
            log.info("✅ Usuario administrador creado con éxito.");
        } else {
            log.info("El usuario administrador ya existe. No se realizaron cambios.");
        }
    }
}
