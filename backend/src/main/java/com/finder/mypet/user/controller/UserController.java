package com.finder.mypet.user.controller;

import com.finder.mypet.jwt.dto.response.JwtResponse;
import com.finder.mypet.user.dto.request.UserJoinRequest;
import com.finder.mypet.user.dto.request.UserLoginRequest;
import com.finder.mypet.user.dto.response.JoinSuccessResponse;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.getUserId(), dto.getPassword(), dto.getNickname());
        JoinSuccessResponse response = JoinSuccessResponse.toDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 로그인 - access token 발급
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto) {
        JwtResponse token = userService.login(dto.getUserId(), dto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    // 회원정보조회
//    @GetMapping("/user/mypage")
//    public ResponseEntity<?> getInfo(@AuthenticationPrincipal Principal principal) {
//        System.out.println("principal = " + principal);
//        return ResponseEntity.ok("o");
//    }


    /*
    // ex.인증된 사람만 게시판에 글 쓰기
    @PostMapping("/board/*")
    public ResponseEntity<String> writeReview(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName() + "님의 리뷰 등록이 완료 되었음");
    }
     */

}
