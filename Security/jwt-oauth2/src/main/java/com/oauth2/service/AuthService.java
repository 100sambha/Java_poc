package com.oauth2.service;

import com.oauth2.config.jwtConfig.JwtTokenGenerator;
import com.oauth2.dto.AuthResponseDto;
import com.oauth2.dto.TokenType;
import com.oauth2.dto.UserRegistrationDto;
import com.oauth2.entity.RefreshTokenEntity;
import com.oauth2.entity.UserInfoEntity;
import com.oauth2.mapper.UserInfoMapper;
import com.oauth2.repo.RefreshTokenRepo;
import com.oauth2.repo.UserInfoRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserInfoRepo userInfoRepo;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserInfoMapper userInfoMapper;

    public AuthResponseDto getJwtTokenAfterAuthentication(Authentication authentication, HttpServletResponse httpServletResponse) {
        try {
            var userInfoEntity = userInfoRepo.findByUserName(authentication.getName())
                    .orElseThrow(()->{
                        log.error("[AuthService:userSignInAuth] User:{} not found",authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
                    });

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            log.info("[AuthService:userSignInAuth] Access Token For User:{}, has been generated",userInfoEntity.getUserName());

            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
            saveRefreshToken(userInfoEntity,refreshToken);
            log.info("[AuthService:userSignInAuth] Refresh Token For User:{}, has been generated",userInfoEntity.getUserName());

            createRefreshTokenCookie(httpServletResponse, refreshToken);

            return AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15*60)
                    .userName(userInfoEntity.getUserName())
                    .tokenType(TokenType.Bearer)
                    .build();
        }catch (Exception e){
            log.error("[AuthService:userSignInAuth]Exception While authenticating user due to :{}",e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
        }
    }

    private Cookie createRefreshTokenCookie(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15*24*60*60);
        httpServletResponse.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    private void saveRefreshToken(UserInfoEntity userInfoEntity, String refreshToken) {
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .userInfo(userInfoEntity)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();

        refreshTokenRepo.save(refreshTokenEntity);
    }

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {
        if (!authorizationHeader.startsWith(TokenType.Bearer.name())){
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Verify Your Token Type");
        }

        final String refreshToken = authorizationHeader.substring(7);
        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(token->!token.isRevoked())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh Token Revoked"));

        UserInfoEntity userInfoEntity = refreshTokenEntity.getUserInfo();

        Authentication authentication = createAuthenticationObject(userInfoEntity);

        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(60)
                .userName(userInfoEntity.getUserName())
                .tokenType(TokenType.Bearer)
                .build();
    }

    private Authentication createAuthenticationObject(UserInfoEntity userInfoEntity) {
        String username = userInfoEntity.getUserName();
        String password = userInfoEntity.getPassword();
        String roles = userInfoEntity.getRoles();

        String[] rolesArray = roles.split(",");
        GrantedAuthority[] grantedAuthority = Arrays.stream(rolesArray)
                .map(role->(GrantedAuthority)role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(grantedAuthority));
    }

    public AuthResponseDto registerUser(@Valid UserRegistrationDto userRegistrationDto, HttpServletResponse httpServletResponse) {
        try {
            log.info("[AuthService:registerUser] User Registration Started With :::{}", userRegistrationDto);
            Optional<UserInfoEntity> user = userInfoRepo.findByUserName(userRegistrationDto.userName());
            if(user.isPresent()){
                throw new Exception("User Already Exist");
            }
            UserInfoEntity userInfo = userInfoMapper.convertToEntity(userRegistrationDto);

            Authentication authentication = createAuthenticationObject(userInfo);

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            UserInfoEntity saveUserDetails = userInfoRepo.save(userInfo);
            saveRefreshToken(userInfo, refreshToken);

            createRefreshTokenCookie(httpServletResponse, refreshToken);

            log.info("[AuthService:registerUser] User:{} successfully registered", saveUserDetails.getUserName());
            return AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(5*60)
                    .userName(saveUserDetails.getUserName())
                    .tokenType(TokenType.Bearer)
                    .build();
        }
        catch(Exception ex){
            log.error("[AuthService:registerUser] Exception while registering the user due to {}",ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}