package com.example.jobala._user;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserRequest {

    @AllArgsConstructor
    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Data
    public static class JoinDTO {
        private String compNum;
        private String ceo;
        private String compname;
        private String address;
        private String username;
        private String email;
        private String password;
        private String name;
        private String phone;
        private Integer role; // 0 -> guest, 1 -> comp

        //개인이 회원가입할때
        public User toGuestEntity() {
            return User.builder()
                    .address(address)
                    .username(username)
                    .email(email)
                    .password(password)
                    .name(name)
                    .phone(phone)
                    .role(role)
                    .build();
        }

        //기업이 회원가입할때
        public User toCompEntity() {
            return User.builder()
                    .compname(compname)
                    .ceo(ceo)
                    .compNum(compNum)
                    .address(address)
                    .username(username)
                    .email(email)
                    .password(password)
                    .name(name)
                    .phone(phone)
                    .role(role)
                    .build();
        }
    }


}
