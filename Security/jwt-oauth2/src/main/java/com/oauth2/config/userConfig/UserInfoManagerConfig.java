package com.oauth2.config.userConfig;

import com.oauth2.repo.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoManagerConfig implements UserDetailsService {

    private final UserInfoRepo userInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("called");
        return userInfoRepo
                .findByUserName(userName)
                .map(UserInfoConfig::new)
                .orElseThrow(()->new UsernameNotFoundException("username: "+userName+" not exists"));
    }
}

//get me the user in the form of auth object(UserDetails)