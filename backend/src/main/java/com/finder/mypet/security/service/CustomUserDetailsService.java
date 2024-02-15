package com.finder.mypet.security.service;

import com.finder.mypet.exception.AppException;
import com.finder.mypet.exception.ErrorCode;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // exception 1. userId - NOTFOUND
        return userRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOTFOUND, userId + "는 존재하지 않는 이메일입니다."));

    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .build();
    }
}
