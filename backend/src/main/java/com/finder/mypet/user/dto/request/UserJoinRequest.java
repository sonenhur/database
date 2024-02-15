package com.finder.mypet.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String userId;
    private String password;
    private String nickname;
}
