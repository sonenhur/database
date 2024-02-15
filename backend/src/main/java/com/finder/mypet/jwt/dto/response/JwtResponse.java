package com.finder.mypet.jwt.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;

    public static JwtResponse create(String accessToken, String refreshToken) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
