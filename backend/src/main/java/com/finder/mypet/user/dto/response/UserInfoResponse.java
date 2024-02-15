package com.finder.mypet.user.dto.response;

import com.finder.mypet.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String userId;
    private String nickname;

    public static UserInfoResponse dto(User user) {
        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
