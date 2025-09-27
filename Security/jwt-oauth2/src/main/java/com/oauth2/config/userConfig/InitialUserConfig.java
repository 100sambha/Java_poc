package com.oauth2.config.userConfig;

import com.oauth2.entity.UserInfoEntity;
import com.oauth2.repo.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InitialUserConfig implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepo userInfoRepo;

    @Override
    public void run(String... args) throws Exception {
        UserInfoEntity user = new UserInfoEntity();
        user.setUserName("sambhaji");
        user.setRoles("ROLE_USER");
        user.setEmailId("sambhaji@gmail.com");
        user.setMobileNumber("9087654132");
        user.setPassword(passwordEncoder.encode("password"));

        UserInfoEntity manager = new UserInfoEntity();
        manager.setUserName("tushar");
        manager.setRoles("ROLE_MANAGER");
        manager.setEmailId("tushar@gmail.com");
        manager.setMobileNumber("9876123409");
        manager.setPassword(passwordEncoder.encode("password"));

        UserInfoEntity admin = new UserInfoEntity();
        admin.setUserName("admin");
        admin.setRoles("ROLE_ADMIN");
        admin.setEmailId("admin@gmail.com");
        admin.setMobileNumber("7654321238");
        admin.setPassword(passwordEncoder.encode("password"));

        userInfoRepo.saveAll(List.of(user,manager,admin));
    }
}
