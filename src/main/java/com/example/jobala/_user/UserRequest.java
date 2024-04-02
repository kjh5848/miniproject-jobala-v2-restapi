package com.example.jobala._user;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.sql.Date;

public class UserRequest {

    //로그인
    @Data
    public static class LoginDTO {
        @NotEmpty(message = "유저네임이 공백일 수 없습니다")
        private String username;
        @NotEmpty(message = "패스워드가 공백일 수 없습니다")
        private String password;
    }

    //회원가입 - 개인, 기업
    @Data
    public static class JoinDTO {
        @NotEmpty(message = "사업자번호가 공백일 수 없습니다")
        private String compNum;
        @NotEmpty(message = "ceo명이 공백일 수 없습니다")
        private String ceo;
        @NotEmpty(message = "기업명이 공백일 수 없습니다")
        private String compname;
        @NotEmpty(message = "주소가 공백일 수 없습니다")
        private String address;
        @NotEmpty(message = "유저네임이 공백일 수 없습니다")
        private String username;
        @NotEmpty(message = "email이 공백일 수 없습니다")
        private String email;
        @NotEmpty(message = "비밀번호가 공백일 수 없습니다")
        private String password;
        @NotEmpty(message = "이름이 공백일 수 없습니다")
        private String name;
        @NotEmpty(message = "전화번호가 공백일 수 없습니다")
        private String phone;
        @NotEmpty(message = "생년월일이 공백일 수 없습니다")
        private Date age;
        @Min(0)
        @Max(1)
        @NotEmpty
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
                    .age(age)
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
                    .age(age)
                    .role(role)
                    .build();
        }
    }

}
